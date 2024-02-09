package com.example.data.repository

import com.example.data.local.dao.RatesDao
import com.example.data.mapper.DatabaseMapper
import com.example.domain.model.RatesStorageModel
import com.example.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val dao: RatesDao,
    private val mapper: DatabaseMapper
): DatabaseRepository {
    override suspend fun insertRatesData(data: List<RatesStorageModel>) {
        val entities = data.map { mapper.mapToEntity(it) }
        dao.insertRatesData(entities)
    }

    override suspend fun getAllRates(): List<RatesStorageModel> {
        return dao.getAllRates().map { mapper.mapFromEntity(it) }
    }
}