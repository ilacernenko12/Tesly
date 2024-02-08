package com.example.tesly.di

import com.example.data.mapper.CurrencyNetworkMapper
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
    fun provideCurrencyDataMapper(): CurrencyNetworkMapper {
        return CurrencyNetworkMapper()
    }

    /*@Provides
    @Singleton
    fun provideCurrencyDomainMapper(): CurrencyDomainMapper{
        return CurrencyDomainMapper()
    }*/
}