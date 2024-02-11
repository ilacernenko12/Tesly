package com.example.presentation.model

import android.graphics.drawable.Drawable

data class CartUiData (
    val scale: String = "",
    val rateName: String,
    val officialRate: String,
    val decemberDiff: String ,
    val yesterdayDiff: String,
    val isPositiveDecemberDiff: Boolean,
    val isPositiveYesterdayDiff: Boolean,
)