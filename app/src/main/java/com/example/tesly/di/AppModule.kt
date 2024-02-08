package com.example.tesly.di

import android.app.Application
import android.content.Context
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
object AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
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