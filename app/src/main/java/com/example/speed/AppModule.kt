package com.example.speed.model.client

import android.util.Log
import com.example.speed.model.repositories.CountryRepo
import com.example.speed.model.repositories.CountryRepoImpl
import com.example.speed.model.services.CountryService
import com.example.speed.viewModel.CountryViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        HttpClient(Android) {
            expectSuccess = true
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("Logger", message)
                    }
                }
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                    allowSpecialFloatingPointValues = true
                    useArrayPolymorphism = true
                })
            }
        }
    }
    single {
        CountryService(get())
    }
    single<CountryRepo> {
        CountryRepoImpl(get())
    }
    viewModel {
        CountryViewModel(get())
    }
}