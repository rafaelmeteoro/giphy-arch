package com.meteoro.repository

import com.meteoro.localdata.GifDao
import com.meteoro.localdata.data.FavoriteGif
import com.meteoro.network.GifRemoteDataSource
import com.meteoro.network.data.GifData
import com.meteoro.network.data.GifImage
import com.meteoro.network.data.GifImageData
import com.meteoro.network.data.GifResponse
import com.meteoro.repository.data.Gif
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

private val GIF_REMOTE =
    GifResponse(
        data = listOf(
            GifData(
                type = "gif",
                id = "123",
                title = "title",
                images = GifImage(
                    previewGif = GifImageData(
                        url = "http://giphy.com"
                    )
                )
            )
        )
    )

private val GIF_DATABASE = listOf(
    FavoriteGif(
        id = "123",
        url = "http://giphy.com",
        title = "title"
    )
)

private val GIF_REPOSITORY_LIST = listOf(
    Gif(
        id = "123",
        url = "http://giphy.com",
        title = "title"
    )
)

private val GIF_REPOSITORY_FAVORITE_LIST = listOf(
    Gif(
        id = "123",
        url = "http://giphy.com",
        title = "title",
        favorite = true
    )
)

class GifRepositoryTest {

    private val gifRemoteDataSource = mockk<GifRemoteDataSource>()
    private val gifDao = mockk<GifDao>(relaxed = true)
    private val gifRepository = GifRepository(gifRemoteDataSource, gifDao)

    @Test
    fun whenRemoteGifDataIsRequestes_gifsIsReturned() = runBlocking {
        coEvery { gifRemoteDataSource.getGIFs() } returns GIF_REMOTE

        val valueReturned = gifRepository.getGIFs()

        assertEquals(GIF_REPOSITORY_LIST.size, valueReturned.size)
        GIF_REPOSITORY_LIST.first().let { gif ->
            assertEquals(gif.id, valueReturned.first().id)
            assertEquals(gif.url, valueReturned.first().url)
            assertEquals(gif.title, valueReturned.first().title)
            assertEquals(gif.favorite, valueReturned.first().favorite)
        }
    }

    @Test
    fun whenThereIsFavoriteGifOnDatabase_favoriteGifIsReturned() = runBlocking {
        coEvery { gifRemoteDataSource.getGIFs() } returns GIF_REMOTE
        every { gifDao.getAll() } returns GIF_DATABASE

        val valueReturned = gifRepository.getGIFs()

        assertEquals(GIF_REPOSITORY_LIST.size, valueReturned.size)
        GIF_REPOSITORY_FAVORITE_LIST.first().let { gif ->
            assertEquals(gif.id, valueReturned.first().id)
            assertEquals(gif.url, valueReturned.first().url)
            assertEquals(gif.title, valueReturned.first().title)
            assertEquals(gif.favorite, valueReturned.first().favorite)
        }
    }
}
