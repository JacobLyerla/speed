package com.example.speed.model.repositories

import com.example.speed.model.data.Country
import com.example.speed.model.data.Currency
import com.example.speed.model.data.Language
import com.example.speed.model.dtos.CountryDTO
import com.example.speed.model.services.CountryService
import com.example.speed.model.util.CountryResult

class CountryRepoImpl(private val api: CountryService) : CountryRepo {
    override suspend fun getCountries(): CountryResult<List<Country>> {

        return try {
            val response: CountryResult<List<CountryDTO>> = api.getCountries()
            when (response) {
                is CountryResult.Success -> {
                    val countries = response.data.map { countryDTO ->
                        Country(
                            capital = countryDTO.capital,
                            code = countryDTO.code,
                            currency = Currency(
                                code = countryDTO.currency.code,
                                name = countryDTO.currency.name,
                                symbol = countryDTO.currency.symbol
                            ),
                            flag = countryDTO.flag,
                            language = Language(
                                code = countryDTO.language.code,
                                name = countryDTO.language.name
                            ),
                            name = countryDTO.name,
                            region = countryDTO.region
                        )
                    }
                    CountryResult.Success(countries)
                }

                is CountryResult.Failure -> {
                    CountryResult.Failure(response.exception)
                }

                CountryResult.Loading -> {
                    CountryResult.Loading
                }
            }
        } catch (e: Exception) {
            CountryResult.Failure(e)
        }
    }
}