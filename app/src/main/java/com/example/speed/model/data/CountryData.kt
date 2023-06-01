package com.example.speed.model.data

data class Country(
    val capital: String,
    val code: String,
    val currency: Currency,
    val flag: String,
    val language: Language,
    val name: String,
    val region: String
)

data class Currency(
    val code: String,
    val name: String,
    val symbol: String? = null
)

data class Language(
    val code: String? = null,
    val name: String
)

