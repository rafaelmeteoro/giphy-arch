package com.meteoro.repository

import com.meteoro.localdata.GifDao
import com.meteoro.localdata.data.FavoriteGif
import com.meteoro.network.GifRemoteDataSource
import com.meteoro.network.data.GifResponse
import com.meteoro.repository.data.Gif
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GifRepository(private val gifRemoteDataSource: GifRemoteDataSource, private val gifDao: GifDao) {

    suspend fun getGIFs(): List<Gif> {
        val apiData = gifRemoteDataSource.getGIFs()

        return getGifFromApiData(apiData)
    }

    private suspend fun getGifFromApiData(apiData: GifResponse): List<Gif> =
        withContext(Dispatchers.IO) {
            val favoriteIDs = gifDao.getAll().map {
                it.id
            }

            apiData.data
                .filter { gifData -> gifData.id != null }
                .map { gifData ->
                    Gif(
                        id = gifData.id ?: "",
                        url = gifData.images?.previewGif?.url ?: "",
                        title = gifData.title ?: "",
                        favorite = favoriteIDs.contains(gifData.id)
                    )
                }
        }

    suspend fun favoriteGif(gif: Gif) {
        withContext(Dispatchers.IO) {
            val favoriteGif = gif.run { FavoriteGif(id = id, url = url, title = title) }
            gifDao.insertAll(favoriteGif)
        }
    }

    suspend fun removeFavoriteGif(gif: Gif) {
        withContext(Dispatchers.IO) {
            val favoriteGif = gif.run { FavoriteGif(id = id, url = url, title = title) }
            gifDao.delete(favoriteGif)
        }
    }

    suspend fun getFavoriteGif(): List<Gif> =
        withContext(Dispatchers.IO) {
            gifDao.getAll().map { favoriteGif ->
                Gif(
                    id = favoriteGif.id,
                    url = favoriteGif.url,
                    title = favoriteGif.title,
                    favorite = true
                )
            }
        }
}
