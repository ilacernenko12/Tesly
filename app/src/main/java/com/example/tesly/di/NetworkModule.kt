package com.example.tesly.di

import com.example.data.mapper.CurrencyDataMapper
import com.example.data.remote.CurrencyApi
import com.example.data.repository.CurrencyRepositoryImpl
import com.example.data.util.Constants.CURRENCY_API_URL
import com.example.domain.repository.CurrencyRepository
import com.example.domain.usecase.GetAllRatesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(CURRENCY_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCurrencyApi(retrofit: Retrofit): CurrencyApi {
        return retrofit.create(CurrencyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCurrencyRepository(api: CurrencyApi): CurrencyRepository {
        return CurrencyRepositoryImpl(mapper = CurrencyDataMapper(), api = api)
    }

    @Provides
    @Singleton
    fun provideGetAllRatesUseCase(repository: CurrencyRepository): GetAllRatesUseCase {
        return GetAllRatesUseCase(repository = repository)
    }
}