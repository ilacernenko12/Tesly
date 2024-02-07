package com.example.presentation.model

import android.graphics.Color

data class RateUIModel(
    val name: String,
    val officialRate: String,
    val difference: String = "",
    val color: Int = Color.BLACK
)
