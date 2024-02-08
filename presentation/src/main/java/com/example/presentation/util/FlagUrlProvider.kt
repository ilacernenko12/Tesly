package com.example.presentation.util

import android.content.Context

class FlagUrlProvider(private val context: Context) {

    // Метод для получения URL-адреса флага страны по аббревиатуре валюты
    fun getFlagUrl(currencyAbbreviation: String): String {
        val resourceName = "flag_${currencyAbbreviation.lowercase()}"
        // Получаем идентификатор ресурса по имени
        val resourceId = context.resources.getIdentifier(resourceName, "drawable", context.packageName)
        // Если ресурс существует, возвращаем его URL-адрес
        return if (resourceId != 0) {
            "drawable://${resourceId}"
        } else {
            // Если ресурс не найден, возвращаем URL-адрес по умолчанию или пустую строку
            ""
        }
    }
}