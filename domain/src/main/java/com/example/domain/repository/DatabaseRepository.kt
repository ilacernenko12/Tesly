package com.example.domain.repository

import com.example.domain.model.RatesStorageModel
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    suspend fun insertRatesData(data: List<RatesStorageModel>)

    suspend fun getAllRates(): List<RatesStorageModel>
}