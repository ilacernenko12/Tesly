package com.example.presentation.util

import kotlin.math.pow

fun Double.roundTo(decimalPlaces: Int): Double {
    val factor = 10.0.pow(decimalPlaces.toDouble())
    return (this * factor).toLong() / factor
}

fun <T> MutableList<T>.moveLastToFront() {
    if (this.isNotEmpty()) {
        val lastElement = this.removeAt(this.size - 1)
        this.add(0, lastElement)
    }
}

fun <K, V> Map<K, V>.reverseEntries(): Map<K, V> {
    val reversedMap = LinkedHashMap<K, V>()
    this.entries.reversed().forEach { (key, value) ->
        reversedMap[key] = value
    }
    return reversedMap
}