package com.onefootball.ui

import android.annotation.SuppressLint
import androidx.lifecycle.*
import com.onefootball.commons.SingleLiveEvent
import com.onefootball.data.NewsRepository
import com.onefootball.ui.model.News
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent

class NewsViewModel(
    private val repository: NewsRepository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel(), LifecycleObserver, KoinComponent {

    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<SingleLiveEvent<Throwable>>()
    val success = MutableLiveData<List<News>>()


    @SuppressLint("CheckResult")
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun loadNews() {
        repository
            .getNews()
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
                    error.value = SingleLiveEvent(it)
                }
            )
            .apply {
                compositeDisposable.add(this)
            }
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}