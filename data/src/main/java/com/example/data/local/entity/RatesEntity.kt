package com.example.data.local.entity

import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rates_entity")
data class RatesEntity(
    @PrimaryKey
    val id: Int,
    val flagUrl: String,
    val name: String,
    val rate: Double,
    val difference: Double,
    val color: Int = Color.BLACK
)
