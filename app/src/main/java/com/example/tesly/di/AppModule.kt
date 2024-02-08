package com.example.tesly.di

import android.app.Application
import android.content.Context
import com.example.presentation.mapper.CurrencyUiMapper
import com.example.presentation.util.FlagUrlProvider
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
    fun provideFlagUrlProvider(context: Context): FlagUrlProvider {
        return FlagUrlProvider(context)
    }

    @Provides
    @Singleton
    fun provideCurrencyUiMapper(flagUrlProvider: FlagUrlProvider): CurrencyUiMapper {
        return CurrencyUiMapper(flagUrlProvider)
    }
}