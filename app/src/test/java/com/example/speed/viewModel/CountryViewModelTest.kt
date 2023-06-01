package com.example.speed.viewModel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.speed.model.data.Country
import com.example.speed.model.data.Currency
import com.example.speed.model.data.Language
import com.example.speed.model.repositories.CountryRepo
import com.example.speed.model.util.CountryResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class CountryViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: CountryRepo
    private lateinit var viewmodel: CountryViewModel
    private lateinit var observer: Observer<CountryResult<List<Country>>>

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        viewmodel = CountryViewModel(repository)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        if (::observer.isInitialized) {
            viewmodel.countriesLiveData.removeObserver(observer)
        }
        Dispatchers.resetMain()
    }

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


    @Test
    fun testSuccessfulResponse() = runTest {
        // given
        val currency = Currency("USD", "United States Dollar", "$")
        val language = Language("en", "English")
        val countries = listOf(
            Country(
                "Washington, D.C.",
                "US",
                currency,
                "https://path/to/flag",
                language,
                "United States",
                "Americas"
            )
        )

        coEvery { repository.getCountries() } coAnswers {
            CountryResult.Success(countries)
        }

        // Prepare LiveData observer
        observer = mockk(relaxed = true)
        viewmodel.countriesLiveData.observeForever(observer)

        // when
        launch { viewmodel.getCountries() }
        advanceUntilIdle()

        // then
        val capturedState = viewmodel.countriesLiveData.value
        assert(capturedState is CountryResult.Success)
        assert((capturedState as CountryResult.Success).data == countries)
        coVerify { observer.onChanged(capturedState) }

    }

}
