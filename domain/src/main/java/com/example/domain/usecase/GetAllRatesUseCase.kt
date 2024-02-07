package com.example.domain.usecase

import com.example.domain.model.RateModel
import com.example.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetAllRatesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend fun getCurrentRates(): List<RateModel> {
      return repository.getCurrentRates()
    }

    private suspend fun getYesterdayRates(date: String): List<RateModel> {
        return repository.getYesterdayRates(date)
    }

    suspend fun getRateDifferences(date: String): List<RateModel> {
        val currentRates = getCurrentRates()
        val yesterdayRates = getYesterdayRates(date)
        val differences = mutableListOf<String>()

        return currentRates.mapIndexed { index, currentRate ->
            val yesterdayRate = yesterdayRates[index]
            val difference = currentRate.officialRate - yesterdayRate.officialRate
            val formattedDifference = if (difference != 0.0) difference else 0.0
            currentRate.copy(difference = formattedDifference)
        }
    }

}