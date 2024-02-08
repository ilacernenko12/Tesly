package com.example.domain.repository

import com.example.domain.model.RatesStorageModel
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    suspend fun insertRatesData(data: RatesStorageModel)

    suspend fun getAllRates(): Flow<List<RatesStorageModel>>
}