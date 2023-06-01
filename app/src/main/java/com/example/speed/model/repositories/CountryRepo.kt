package com.example.speed.model.repositories

import com.example.speed.model.data.Country
import com.example.speed.model.util.CountryResult

interface CountryRepo {

    suspend fun getCountries(): CountryResult<List<Country>>
}