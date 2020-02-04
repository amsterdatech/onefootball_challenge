package com.onefootball.ui

import androidx.lifecycle.*
import com.onefootball.commons.SingleLiveEvent
import com.onefootball.data.NewsRepository
import com.onefootball.ui.model.News
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

class NewsViewModel(
    private val repository: NewsRepository,
    private val compositeDisposable: CompositeDisposable,
    private val mainScheduler: Scheduler

) : ViewModel(), LifecycleObserver {

    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<SingleLiveEvent<Throwable>>()
    val success = MutableLiveData<List<News>>()


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun loadNews() {
        repository
            .getNews()
            .observeOn(mainScheduler)
            .doOnSubscribe {
                loading.value = true
            }
            .subscribe(
                {
                    loading.value = false
                    success.value = it
                },
                {
                    loading.value = false
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