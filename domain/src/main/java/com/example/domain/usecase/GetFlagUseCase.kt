package com.example.domain.usecase

import com.example.domain.model.FlagModel
import com.example.domain.repository.FlagRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFlagUseCase @Inject constructor(
    private val repository: FlagRepository
) {
    suspend fun getFlag(currency: String): Flow<FlagModel> = flow {
        val data = repository.getFlag(currency)
        emit(data)
    }
}