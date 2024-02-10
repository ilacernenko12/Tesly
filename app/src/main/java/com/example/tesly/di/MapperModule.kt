package com.example.tesly.di

import com.example.data.mapper.CurrencyNetworkMapper
import com.example.presentation.mapper.CurrencyUiMapper
import com.example.presentation.mapper.FlagUiMapper
import com.example.presentation.mapper.TableUiMapper
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

    @Provides
    @Singleton
    fun provideFlagUiMapper(): FlagUiMapper {
        return FlagUiMapper()
    }

    @Provides
    @Singleton
    fun provideCurrencyUiMapper(): CurrencyUiMapper {
        return CurrencyUiMapper()
    }

    @Provides
    @Singleton
    fun provideTableUiMapper(): TableUiMapper {
        return TableUiMapper()
    }
}