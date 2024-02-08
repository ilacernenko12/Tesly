package com.example.domain.usecase

import com.example.domain.model.RatesStorageModel
import com.example.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDataFromDatabaseUseCase @Inject constructor(
    private val repository: DatabaseRepository
) {
    suspend fun insertRatesData(data: RatesStorageModel) {
        repository.insertRatesData(data)
    }

    suspend fun getAllRates(): Flow<List<RatesStorageModel>> {
        return repository.getAllRates()
    }
}