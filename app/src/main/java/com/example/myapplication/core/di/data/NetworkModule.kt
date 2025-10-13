package com.example.myapplication.core.di.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import org.koin.dsl.module

val networkModule = module {
    single<HttpClient> {
        HttpClient(Android)
    }
}