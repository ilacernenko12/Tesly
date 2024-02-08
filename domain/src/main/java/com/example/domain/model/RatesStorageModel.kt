package com.example.domain.model

import android.graphics.Color

data class RatesStorageModel (
    val id: Int,
    val flagUrl: String,
    val name: String,
    val rate: Double,
    val difference: Double,
    val color: Int = Color.BLACK
)