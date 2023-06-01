package com.example.speed.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.speed.model.data.Country
import com.example.speed.model.repositories.CountryRepo
import com.example.speed.model.util.CountryResult
import kotlinx.coroutines.delay

class CountryViewModel(private val repo: CountryRepo) : ViewModel() {
    private val _countriesLiveData = MutableLiveData<CountryResult<List<Country>>>()
    val countriesLiveData: LiveData<CountryResult<List<Country>>> = _countriesLiveData

    suspend fun getCountries() {
        _countriesLiveData.value = CountryResult.Loading

        delay(3000)

        val result = repo.getCountries()

        _countriesLiveData.value = result

    }
}
