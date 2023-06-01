package com.example.speed.model

import com.example.speed.model.data.Country
import com.example.speed.model.data.Currency
import com.example.speed.model.data.Language
import com.example.speed.model.dtos.CountryDTO
import com.example.speed.model.dtos.CurrencyDTO
import com.example.speed.model.dtos.LanguageDTO
import com.example.speed.model.repositories.CountryRepoImpl
import com.example.speed.model.services.CountryService
import com.example.speed.model.util.CountryResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CountryRepoTest {


    private lateinit var service: CountryService
    private lateinit var repo: CountryRepoImpl

    @Before
    fun setup() {
        service = mockk()
        repo = CountryRepoImpl(service)
    }

    @Test
    fun `getCountries returns successful result when service provides countries`() = runTest {
        // given
        lateinit var result: CountryResult<List<Country>>

        val currencyDTO = CurrencyDTO("USD", "United States Dollar", "$")
        val languageDTO = LanguageDTO("en", "English")
        val response = listOf(
            CountryDTO(
                "Washington, D.C.",
                "US",
                currencyDTO,
                "https://path/to/flag",
                languageDTO,
                "United States",
                "Americas"
            )
        )
        //----
        val currency = Currency("USD", "United States Dollar", "$")
        val language = Language("en", "English")
        val expected = listOf(
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
        coEvery { service.getCountries() } coAnswers { CountryResult.Success(response) }
        launch { result = repo.getCountries() }
        advanceUntilIdle()

        // then
        coVerify { service.getCountries() }
        TestCase.assertTrue(result is CountryResult.Success)
        TestCase.assertTrue((result as CountryResult.Success).data == expected)
        assertEquals(CountryResult.Success(expected), result)
    }

    @Test
    fun `getWordsFromAcronym returns error result when service throws exception`() = runTest {
        // given
        val acronym = "API"
        lateinit var result: CountryResult<List<Country>>
        val exceptionMessage = "Failed to retrieve meanings"
        coEvery { service.getCountries() } coAnswers { throw Exception(exceptionMessage) }
        launch { result = repo.getCountries() }
        advanceUntilIdle()

        // then
        TestCase.assertTrue(result is CountryResult.Failure)
    }
}