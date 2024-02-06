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
}