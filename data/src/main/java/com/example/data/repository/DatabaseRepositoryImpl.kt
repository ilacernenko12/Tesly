package com.example.data.repository

import com.example.data.local.dao.RatesDao
import com.example.data.mapper.CartDbMapper
import com.example.data.mapper.RatesDbMapper
import com.example.domain.model.CartModel
import com.example.domain.model.RatesStorageModel
import com.example.domain.repository.DatabaseRepository
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val dao: RatesDao,
    private val ratesMapper: RatesDbMapper,
    private val cartMapper: CartDbMapper
): DatabaseRepository {
    override suspend fun insertRatesData(data: List<RatesStorageModel>) {
        val entities = data.map { ratesMapper.mapToEntity(it) }
        dao.insertRatesData(entities)
    }

    override suspend fun getAllRates(): List<RatesStorageModel> {
        return dao.getAllRates().map { ratesMapper.mapFromEntity(it) }
    }

    override suspend fun insertCartData(data: List<CartModel>) {
        val entities = data.map { cartMapper.mapToEntity(it) }
        dao.deleteCartData()
        dao.insertCartDara(entities)
    }

    override suspend fun getCartData(): List<CartModel> {
        return dao.getCartData().map { cartMapper.mapFromEntity(it) }
    }
}