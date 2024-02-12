package com.example.data.repository

import com.example.data.mapper.FlagNetworkMapper
import com.example.data.remote.CurrencyApi
import com.example.domain.model.FlagModel
import com.example.domain.repository.FlagRepository
import javax.inject.Inject

class FlagRepositoryImpl @Inject constructor(
    val api: CurrencyApi,
    val mapper: FlagNetworkMapper
) : FlagRepository {
    override suspend fun getFlag(currency: String):FlagModel {
        return mapper.mapFromEntity(api.getFlags(currency).first())
    }
}