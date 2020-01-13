package com.onefootball.di

import com.onefootball.data.NewsRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { NewsRepository() }
}