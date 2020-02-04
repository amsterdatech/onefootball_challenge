package com.onefootball

import android.app.Application
import com.onefootball.commons.Browser
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

        const val IO_SCHEDULER = "IO_SCHEDULER"
        const val MAIN_SCHEDULER = "MAIN_SCHEDULER"
        const val LOCAL_SOURCE = "LOCAL_SOURCE"
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        startKoin {
            androidContext(this@App)
            androidLogger(Level.DEBUG)
            modules(listOf(viewModelModule, repositoryModule))
        }

        Browser.warm(this)

    }
}