package com.example.stox.data.api

import com.example.stox.data.model.HoldingData
import retrofit2.http.GET

interface ApiService {
    @GET("userHoldings")
    suspend fun getUserHoldings(): HoldingData
}
