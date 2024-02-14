package com.example.presentation.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.pow

private val yyyyMMdd = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
private val ddMMyyyy = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

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

fun Date.previousDateAsString(): String {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.DAY_OF_YEAR, -1)
    return yyyyMMdd.format(calendar.time)
}

fun dateStringStartOfYear(format: Date): String {
    return yyyyMMdd.format(format)
}

fun dateStringStartOfDay(format: Date): String {
    return ddMMyyyy.format(format)
}