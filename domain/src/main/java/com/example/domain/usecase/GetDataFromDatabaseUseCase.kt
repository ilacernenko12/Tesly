package com.example.domain.usecase

import com.example.domain.model.CartModel
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

    suspend fun isRatesDataAvailable(): Boolean {
        return repository.getAllRates().isNotEmpty()
    }

    suspend fun isCartDataAvailable(): Boolean {
        return repository.getCartData().isNotEmpty()
    }

    suspend fun getCartData(): List<CartModel> {
        return repository.getCartData()
    }

    suspend fun insertCartData(data: List<CartModel>) {
        repository.insertCartData(data)
    }
}

