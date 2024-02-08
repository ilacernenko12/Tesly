package com.example.tesly.di

import com.example.data.mapper.CurrencyDataMapper
import com.example.data.mapper.FlagDataMapper
import com.example.data.remote.CurrencyApi
import com.example.data.repository.CurrencyRepositoryImpl
import com.example.data.repository.FlagRepositoryImpl
import com.example.data.util.Constants.CURRENCY_API_URL
import com.example.domain.repository.CurrencyRepository
import com.example.domain.repository.FlagRepository
import com.example.domain.usecase.GetAllRatesUseCase
import com.example.domain.usecase.GetFlagUseCase
import com.example.presentation.mapper.CurrencyUiMapper
import com.example.presentation.mapper.FlagUiMapper
import com.example.presentation.ui.rates.AllRatesViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(25, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(CURRENCY_API_URL)
            .client(okHttpClient)
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

    @Provides
    @Singleton
    fun provideFlagRepository(api: CurrencyApi): FlagRepository {
        return FlagRepositoryImpl(mapper = FlagDataMapper(), api = api)
    }

    @Provides
    @Singleton
    fun provideGetFlagUseCase(repository: FlagRepository): GetFlagUseCase {
        return GetFlagUseCase(repository = repository)
    }
}