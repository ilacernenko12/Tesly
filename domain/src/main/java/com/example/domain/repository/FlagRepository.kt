package com.example.domain.repository

import com.example.domain.model.FlagModel
import kotlinx.coroutines.flow.Flow

interface FlagRepository {
    suspend fun getFlag(currency: String): FlagModel
}