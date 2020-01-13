package com.onefootball

import android.app.Application

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
    }
}