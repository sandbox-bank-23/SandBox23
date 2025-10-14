package com.example.myapplication.auth.di.domain

import com.example.myapplication.auth.domain.AuthInteractor
import com.example.myapplication.auth.domain.impl.AuthInteractorImpl
import org.koin.dsl.module

val domainModule = module {

    single<AuthInteractor> {
        AuthInteractorImpl(get())
    }
}