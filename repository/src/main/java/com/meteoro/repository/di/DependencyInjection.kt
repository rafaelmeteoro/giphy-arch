package com.meteoro.repository.di

import com.meteoro.localdata.GifDatabaseBuilder
import com.meteoro.network.GifRemoteDataSource
import com.meteoro.network.service.GifAPIBuilder
import com.meteoro.repository.GifRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single { GifAPIBuilder().build() }
    single { GifRemoteDataSource(get()) }
    single { GifDatabaseBuilder(androidContext()).build().gifDao() }
    factory { GifRepository(get(), get()) }
}
