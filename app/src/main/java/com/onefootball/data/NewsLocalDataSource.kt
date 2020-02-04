package com.onefootball.data

import android.content.Context
import com.google.gson.Gson
import com.onefootball.extensions.loadJsonStringFromFile
import com.onefootball.ui.model.News
import io.reactivex.Scheduler
import io.reactivex.Single

class NewsLocalDataSource(
    private val serializer: Gson,
    private val context: Context,
    private val ioScheduler: Scheduler

) :
    NewsRepository {

    override fun getNews(): Single<List<News>> {
        return Single.fromCallable {
            val apiResponse = serializer.fromJson(
                context.loadJsonStringFromFile(),
                ApiResponse::class.java
            )

            apiResponse.news
                .map {
                    News(
                        it.title,
                        it.imageURL,
                        it.resourceName,
                        it.resourceURL,
                        it.newsLink
                    )
                }
        }
            .subscribeOn(ioScheduler)
    }
}
