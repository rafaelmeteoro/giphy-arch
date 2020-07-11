package com.meteoro.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meteoro.repository.GifRepository
import com.meteoro.repository.data.Gif
import kotlinx.coroutines.launch

class FavoriteGifListViewModel(private val gifRepository: GifRepository) : ViewModel() {

    private val _gifList = MutableLiveData<List<Gif>>()
    val gifList: LiveData<List<Gif>>
        get() = _gifList

    init {
        viewModelScope.launch {
            _gifList.postValue(gifRepository.getFavoriteGif())
        }
    }

    fun removeFavorite(gif: Gif) {
        viewModelScope.launch {
            gifRepository.removeFavoriteGif(gif)
            removeItemfromgifList(gif)
        }
    }

    private fun removeItemfromgifList(gif: Gif) {
        val mutableGifList = _gifList.value?.toMutableList()
        mutableGifList?.remove(gif)
        _gifList.postValue(mutableGifList)
    }
}
