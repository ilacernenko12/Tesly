package com.example.data.repository

import com.example.data.mapper.CurrencyDataMapper
import com.example.data.remote.CurrencyApi
import com.example.domain.repository.CurrencyRepository
import com.example.domain.model.RateModel
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val mapper: CurrencyDataMapper,
    private val api: CurrencyApi
): CurrencyRepository {
    override suspend fun getCurrentRates(): List<RateModel> {
        return api.getCurrentRates().map { rateResponse ->
            mapper.mapFromEntity(rateResponse)
        }
    }

    override suspend fun getYesterdayRates(date: String): List<RateModel> {
        return api.getYesterdayRates(date = date).map { rateResponse ->
            mapper.mapFromEntity(rateResponse)
        }
    }
}