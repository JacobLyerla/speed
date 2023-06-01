package com.example.speed.model.util

sealed class CountryResult<out T> {
    data class Success<out T>(val data: T) : CountryResult<T>()
    data class Failure(val exception: Exception) : CountryResult<Nothing>()
    object Loading : CountryResult<Nothing>()
}