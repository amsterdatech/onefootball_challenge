package com.onefootball.di

import com.onefootball.App.Companion.LOCAL_SOURCE
import com.onefootball.App.Companion.MAIN_SCHEDULER
import com.onefootball.ui.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        NewsViewModel(
            get(named(LOCAL_SOURCE)),
            get(),
            get(named(MAIN_SCHEDULER))
        )
    }
}