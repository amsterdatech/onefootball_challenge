package com.onefootball

import android.app.Application
import com.onefootball.di.repositoryModule
import com.onefootball.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    companion object {
        lateinit var instance: App
            private set

        fun newsJson(): String {
            return instance.assets.open("news.json")
                .bufferedReader()
                .use {
                    it.readText()
                }
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        startKoin {
            androidContext(this@App)
            androidLogger(Level.DEBUG)
            modules(listOf(viewModelModule, repositoryModule))
        }
    }
}