package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_entity")
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val scale: String = "",
    val rateName: String,
    val officialRate: Double,
    val decemberDiff: Double ,
    val yesterdayDiff: Double,
    val isPositiveDecemberDiff: Boolean,
    val isPositiveYesterdayDiff: Boolean
)
