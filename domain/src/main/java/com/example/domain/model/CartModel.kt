package com.example.domain.model

data class CartModel(
    val id: Int,
    val scale: String = "",
    val rateName: String,
    val officialRate: Double,
    val decemberDiff: Double ,
    val yesterdayDiff: Double,
    val isPositiveDecemberDiff: Boolean,
    val isPositiveYesterdayDiff: Boolean,
)
