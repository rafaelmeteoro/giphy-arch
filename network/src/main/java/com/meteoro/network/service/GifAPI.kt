package com.meteoro.network.service

import com.meteoro.network.BuildConfig
import com.meteoro.network.data.GifResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GifAPI {
    @GET("tranding")
    suspend fun getTrendingGIFs(@Query("api_key") apiKey: String = BuildConfig.GIPHY_API_KEY): GifResponse
}
