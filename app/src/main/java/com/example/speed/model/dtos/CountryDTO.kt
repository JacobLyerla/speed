package com.example.speed.model.dtos

import kotlinx.serialization.Serializable

@Serializable
data class CountryDTO(
    val capital: String = "",
    val code: String = "",
    val currency: CurrencyDTO = CurrencyDTO(),
    val flag: String = "",
    val language: LanguageDTO = LanguageDTO(),
    val name: String = "",
    val region: String = "",
)

@Serializable
data class CurrencyDTO(
    val code: String = "",
    val name: String = "",
    val symbol: String? = null
)

@Serializable
data class LanguageDTO(
    val code: String? = null,
    val name: String = "",
    val iso639_2: String? = null,
    val nativeName: String? = null
)
