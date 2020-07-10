package com.meteoro.giphy.di

import com.meteoro.giphy.giflist.GifListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { GifListViewModel(get()) }
}
