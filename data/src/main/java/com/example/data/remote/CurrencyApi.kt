package com.example.data.remote

import com.example.data.util.Constants.ON_DATE_PARAM
import com.example.data.util.Constants.PERIODICITY_PARAM
import com.example.data.util.Constants.RATES_ENDPOINT
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET(RATES_ENDPOINT)
    suspend fun getCurrentRates(): List<RateResponse>

    @GET(RATES_ENDPOINT)
    suspend fun getYesterdayRates(
        @Query(ON_DATE_PARAM) date: String,
        @Query(PERIODICITY_PARAM) periodicity: Int = 0
    ): List<RateResponse>
}