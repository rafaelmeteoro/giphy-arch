package com.meteoro.network

import com.meteoro.network.data.GifData
import com.meteoro.network.data.GifImage
import com.meteoro.network.data.GifImageData
import com.meteoro.network.data.GifResponse
import com.meteoro.network.service.GifAPI
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

private val GIF_RESPONSE =
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

@ExperimentalCoroutinesApi
class GifRemoteDataSourceTest {

    private val gifAPI = mockk<GifAPI>()
    private val gifRemoteDataSource = GifRemoteDataSource(gifAPI)

    @Test
    fun whenGifIsRequested_gifFromApiIsreturned() = runBlockingTest {
        coEvery { gifAPI.getTrendingGIFs() } returns GIF_RESPONSE

        val valueReturned = gifRemoteDataSource.getGIFs()

        assertEquals(GIF_RESPONSE, valueReturned)
    }
}
