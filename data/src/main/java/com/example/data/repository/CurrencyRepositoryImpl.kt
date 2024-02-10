package com.example.data.repository

import com.example.data.mapper.CurrencyNetworkMapper
import com.example.data.remote.CurrencyApi
import com.example.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val mapper: CurrencyNetworkMapper,
    private val api: CurrencyApi
): CurrencyRepository {
    override suspend fun getCurrentRates() = flow {
        val rates = api.getCurrentRates().map { mapper.mapFromEntity(it) }
        emit(rates)
    }

    override suspend fun getRatesByDay(date: String) = flow {
        val rates = api.getRatesByDay(date = date).map { mapper.mapFromEntity(it) }
        emit(rates)
    }
}