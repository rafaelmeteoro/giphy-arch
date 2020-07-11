package com.meteoro.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.meteoro.repository.GifRepository
import com.meteoro.repository.data.Gif
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private val FAVORITE_GIF = Gif(
    id = "1",
    url = "test.com",
    title = "test",
    favorite = true
)

private val GIF_LIST = listOf(FAVORITE_GIF)

@ExperimentalCoroutinesApi
class FavoriteGifListViewModelTest {

    @JvmField
    @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val repository = mockk<GifRepository>(relaxed = true)
    private lateinit var favoriteGifViewModel: FavoriteGifListViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockRepository()

        favoriteGifViewModel = FavoriteGifListViewModel(repository)
    }

    @Test
    fun removeFavorite_whenItemIsFavorite_favoriteIsremoved() = runBlockingTest {
        favoriteGifViewModel.removeFavorite(FAVORITE_GIF)

        coVerify { repository.removeFavoriteGif(FAVORITE_GIF) }
        val gifList = favoriteGifViewModel.gifList.value ?: throw NullPointerException()
        assertEquals(0, gifList.size)
    }

    private fun mockRepository() {
        coEvery { repository.getFavoriteGif() } returns GIF_LIST
    }
}
