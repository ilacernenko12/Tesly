package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entity.CartEntity
import com.example.data.local.entity.RatesEntity

@Dao
interface RatesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRatesData(ratesEntity: List<RatesEntity>)

    @Query("SELECT * FROM rates_entity")
    fun getAllRates(): List<RatesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCartDara(cartEntity: List<CartEntity>)

    @Query("SELECT * FROM cart_entity")
    fun getCartData(): List<CartEntity>

    @Query("DELETE FROM cart_entity")
    fun deleteCartData()
}