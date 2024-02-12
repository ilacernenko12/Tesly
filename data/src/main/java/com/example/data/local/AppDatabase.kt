package com.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.dao.RatesDao
import com.example.data.local.entity.CartEntity
import com.example.data.local.entity.RatesEntity

@Database(entities = [RatesEntity::class, CartEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ratesDao(): RatesDao
}