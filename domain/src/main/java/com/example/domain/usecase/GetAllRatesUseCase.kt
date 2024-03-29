package com.example.domain.usecase

import com.example.domain.model.RateModel
import com.example.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetAllRatesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend fun getCurrentRates(): Flow<List<RateModel>> {
        return repository.getCurrentRates()
    }

    suspend fun getRatesByDay(date: String): Flow<List<RateModel>> {
        return repository.getRatesByDay(date)
    }

    suspend fun getRateDifferences(date: String): Flow<List<RateModel>> {
        return combine(getCurrentRates(), getRatesByDay(date)) { currentRates, yesterdayRates ->
            currentRates.zip(yesterdayRates) { currentRate, yesterdayRate ->
                val difference = currentRate.officialRate - yesterdayRate.officialRate
                val formattedDifference = if (difference != 0.0) difference else 0.0
                currentRate.copy(difference = formattedDifference)
            }
        }
    }
}