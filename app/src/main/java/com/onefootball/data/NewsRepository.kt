package com.onefootball.data

import com.google.gson.Gson
import com.onefootball.App
import com.onefootball.ui.model.News
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class NewsRepository {

    fun getNews(): Observable<List<News>> {
        return Observable
            .defer {
                Observable.just(Gson().fromJson(App.newsJson(), ApiResponse::class.java))
            }
            .flatMap { response ->
                Observable.just(
                    response.news.map {
                        News(it.title, it.imageURL, it.resourceName, it.resourceURL, it.newsLink)
                    }
                )
            }
            .subscribeOn(Schedulers.io())
    }
}