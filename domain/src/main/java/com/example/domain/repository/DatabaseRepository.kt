package com.example.domain.repository

import com.example.domain.model.CartModel
import com.example.domain.model.RatesStorageModel
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    suspend fun insertRatesData(data: List<RatesStorageModel>)

    suspend fun getAllRates(): List<RatesStorageModel>

    suspend fun insertCartData(data: List<CartModel>)
    suspend fun getCartData(): List<CartModel>
}