package com.example.presentation.util

import kotlin.math.pow

fun Double.roundTo(decimalPlaces: Int): Double {
    val factor = 10.0.pow(decimalPlaces.toDouble())
    return (this * factor).toLong() / factor
}