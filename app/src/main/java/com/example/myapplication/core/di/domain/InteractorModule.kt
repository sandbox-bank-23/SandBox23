package com.example.myapplication.core.di.domain

import com.example.myapplication.core.domain.api.AppInteractor
import com.example.myapplication.core.domain.impl.AppInteractorImpl
import org.koin.dsl.module

val coreInteractorModule = module {
    single<AppInteractor> {
        AppInteractorImpl()
    }
}