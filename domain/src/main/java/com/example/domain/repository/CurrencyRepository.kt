package com.example.domain.repository

import com.example.domain.model.RateModel
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    suspend fun getCurrentRates(): Flow<List<RateModel>>
    suspend fun getRatesByDay(date: String): Flow<List<RateModel>>
}