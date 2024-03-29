package com.example.tesly.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.AppDatabase
import com.example.data.local.dao.RatesDao
import com.example.data.mapper.CartDbMapper
import com.example.data.mapper.RatesDbMapper
import com.example.data.repository.DatabaseRepositoryImpl
import com.example.domain.repository.DatabaseRepository
import com.example.domain.usecase.GetDataFromDatabaseUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
            .build()
    }

    @Provides
    fun provideRatesDao(database: AppDatabase): RatesDao {
        return database.ratesDao()
    }

    @Provides
    @Singleton
    fun provideDatabaseRepository(dao: RatesDao): DatabaseRepository {
        return DatabaseRepositoryImpl(ratesMapper = RatesDbMapper(), cartMapper = CartDbMapper(), dao = dao)
    }

    @Provides
    @Singleton
    fun provideGetDataFromDatabaseUseCase(repository: DatabaseRepository): GetDataFromDatabaseUseCase {
        return GetDataFromDatabaseUseCase(repository = repository)
    }
}
