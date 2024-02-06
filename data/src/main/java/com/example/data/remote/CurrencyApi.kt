package com.example.data.remote

import com.example.data.util.Constants.CURRENT_RATES_ENDPOINT
import retrofit2.http.GET

interface CurrencyApi {
    @GET(CURRENT_RATES_ENDPOINT)
    suspend fun getCurrentRates(): List<RateResponse>
}