package com.example.domain.repository

import com.example.domain.model.RateModel

interface CurrencyRepository {
    suspend fun getCurrentRates(): List<RateModel>
}