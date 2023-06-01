package com.example.speed.model.services

import android.util.Log
import com.example.speed.model.dtos.CountryDTO
import com.example.speed.model.util.CountryResult
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class CountryService(private val client: HttpClient) {
    suspend fun getCountries(): CountryResult<List<CountryDTO>> {
        return try {
            val httpResponse: HttpResponse = client.get(BASE_URL)
            if (httpResponse.status.value == 200) {
                val stringBody: String =
                    httpResponse.bodyAsText()

                val json =
                    Json { ignoreUnknownKeys = true }
                val response: List<CountryDTO> =
                    json.decodeFromString(stringBody)
                CountryResult.Success(response)
            } else {
                CountryResult.Failure(Exception("Request failed with status code: ${httpResponse.status.value}"))
            }
        } catch (e: Exception) {
            Log.d("Something went wrong:", "${e.localizedMessage}")
            CountryResult.Failure(e)
        }
    }

    companion object {
        private val BASE_URL =
            "https://gist.githubusercontent.com/peymano-wmt/32dcb892b06648910ddd40406e37fdab/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/countries.json"
    }
}
