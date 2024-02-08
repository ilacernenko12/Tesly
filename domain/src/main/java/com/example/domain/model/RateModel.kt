package com.example.domain.model

data class RateModel (
    val abbreviation: String,
    val name: String,
    val officialRate: Double,
    val difference: Double = 0.0
)