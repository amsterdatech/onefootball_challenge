package com.onefootball.di

import com.google.gson.Gson
import com.onefootball.App.Companion.IO_SCHEDULER
import com.onefootball.App.Companion.LOCAL_SOURCE
import com.onefootball.App.Companion.MAIN_SCHEDULER
import com.onefootball.data.NewsLocalDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    single(named(LOCAL_SOURCE)) { NewsLocalDataSource(get(), androidContext()) }
    factory { CompositeDisposable() }
    single(named(IO_SCHEDULER)) { Schedulers.io() }
    single(named(MAIN_SCHEDULER)) { AndroidSchedulers.mainThread() }
    factory { Gson() }
}