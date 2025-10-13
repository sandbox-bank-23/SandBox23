package com.example.myapplication.core.di.data

import com.example.myapplication.core.data.network.NetworkClient
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import org.koin.dsl.module

@Suppress("NoTrailingSpaces")
val networkModule = module {
    single<HttpClient> {
        HttpClient(Android)
    }

    single<NetworkClient> { NetworkClient(get<HttpClient>()) }
}