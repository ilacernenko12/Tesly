package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entity.RatesEntity

@Dao
interface RatesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRatesData(ratesEntity: RatesEntity)

    @Query("SELECT * FROM rates_entity")
    suspend fun getAllRates(): List<RatesEntity>
}