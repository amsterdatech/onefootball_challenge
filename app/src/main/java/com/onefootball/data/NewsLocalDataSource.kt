package com.onefootball.data

import android.content.Context
import com.google.gson.Gson
import com.onefootball.App.Companion.IO_SCHEDULER
import com.onefootball.App.Companion.MAIN_SCHEDULER
import com.onefootball.extensions.loadJsonStringFromFile
import com.onefootball.ui.model.News
import io.reactivex.Observable
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.qualifier.named

class NewsLocalDataSource(
    private val serializer: Gson,
    private val context: Context
) :
    NewsRepository, KoinComponent {

    override fun getNews(): Observable<List<News>> {
        return Observable
            .defer {
                Observable.just(
                    serializer.fromJson(
                        context.loadJsonStringFromFile(),
                        ApiResponse::class.java
                    )
                )
            }
            .flatMap { response ->
                Observable.just(
                    response.news.map {
                        News(
                            it.title,
                            it.imageURL,
                            it.resourceName,
                            it.resourceURL,
                            it.newsLink
                        )
                    }
                )
            }
            .subscribeOn(get(named(IO_SCHEDULER)))
            .observeOn(get(named(MAIN_SCHEDULER)))

    }
}