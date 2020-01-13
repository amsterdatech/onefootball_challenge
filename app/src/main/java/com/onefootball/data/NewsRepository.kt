package com.onefootball.data

import com.onefootball.ui.model.News
import io.reactivex.Observable

interface NewsRepository {
    fun getNews(): Observable<List<News>>
}