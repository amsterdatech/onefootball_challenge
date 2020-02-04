package com.onefootball.data

import com.onefootball.ui.model.News
import io.reactivex.Single

interface NewsRepository {
    fun getNews(): Single<List<News>>
}