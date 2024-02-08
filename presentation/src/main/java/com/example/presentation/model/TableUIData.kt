package com.example.presentation.model

import android.graphics.Color

data class TableUIData(
    val flags: FlagUIModel,
    val rates: RateUIModel
)

data class RateUIModel(
    val name: String,
    val officialRate: String,
    val difference: String = "",
    val color: Int = Color.BLACK
)

data class FlagUIModel (
    val flagUrl: String
)
