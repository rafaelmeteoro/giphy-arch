package com.meteoro.network

import com.meteoro.network.service.GifAPI

class GifRemoteDataSource(private val api: GifAPI) {
    suspend fun getGIFs() = api.getTrendingGIFs()
}
