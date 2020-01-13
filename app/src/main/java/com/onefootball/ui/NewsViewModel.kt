package com.onefootball.ui

import android.annotation.SuppressLint
import androidx.lifecycle.*
import com.onefootball.data.NewsRepository
import com.onefootball.ui.model.News
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class NewsViewModel : ViewModel(), LifecycleObserver {

    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Throwable>()
    val success = MutableLiveData<List<News>>()

    val repository = NewsRepository()

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    @SuppressLint("CheckResult")
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun loadNews() {
        repository
            .getNews()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                loading.value = true
            }
            .doOnComplete {
                loading.value = false
            }
            .subscribe(
                {
                    success.value = it
                },
                {
                    error.value = it
                }
            )
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}