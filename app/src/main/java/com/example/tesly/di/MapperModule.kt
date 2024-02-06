package com.example.tesly.di

import com.example.data.mapper.CurrencyDataMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {
    @Provides
    @Singleton
    fun provideCurrencyDataMapper(): CurrencyDataMapper {
        return CurrencyDataMapper()
    }

    /*@Provides
    @Singleton
    fun provideCurrencyDomainMapper(): CurrencyDomainMapper{
        return CurrencyDomainMapper()
    }*/
}