package com.example.domain.model

data class RateModel (
    val scale: Int = 0,
    val abbreviation: String,
    val name: String,
    val officialRate: Double,
    val difference: Double = 0.0
)