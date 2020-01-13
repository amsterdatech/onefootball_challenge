package com.onefootball.extensions

import android.content.Context

fun Context.loadJsonStringFromFile(filename: String = "news.json"): String {
    return assets.open(filename)
        .bufferedReader()
        .use {
            it.readText()
        }
}