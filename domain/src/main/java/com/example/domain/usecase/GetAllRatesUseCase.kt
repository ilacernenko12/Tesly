package com.example.domain.usecase

import com.example.domain.mapper.CurrencyDomainMapper
import com.example.domain.repository.CurrencyRepository
import com.example.presentation.model.RateUIModel
import javax.inject.Inject

class GetAllRatesUseCase @Inject constructor(
    private val repository: CurrencyRepository,
    private val mapper: CurrencyDomainMapper
) {
    suspend fun getCurrentRates(): List<RateUIModel> {
      return repository.getCurrentRates().map { rateModel ->
          mapper.mapFromModel(rateModel)
      }
    }
}