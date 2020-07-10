package com.meteoro.giphy

import com.google.android.play.core.splitcompat.SplitCompatApplication
import com.meteoro.giphy.di.appModule
import com.meteoro.repository.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GifApplication : SplitCompatApplication() {

    override fun onCreate() {
        super.onCreate()
        loadDependencies()
    }

    private fun loadDependencies() {
        startKoin {
            androidLogger()
            androidContext(this@GifApplication)
            modules(appModule, repositoryModule)
        }
    }
}
