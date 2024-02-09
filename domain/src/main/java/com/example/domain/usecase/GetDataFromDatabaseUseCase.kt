package com.example.domain.usecase

import com.example.domain.model.RatesStorageModel
import com.example.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetDataFromDatabaseUseCase @Inject constructor(
    private val repository: DatabaseRepository
) {
    suspend fun insertRatesData(data: List<RatesStorageModel>) {
        repository.insertRatesData(data)
    }

    suspend fun getAllRates(): List<RatesStorageModel> {
        return repository.getAllRates()
    }

    suspend fun isDataAvailable(): Boolean {
        return repository.getAllRates().isNotEmpty()
    }
}

