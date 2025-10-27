package com.example.myapplication.core.di.data

import com.example.myapplication.core.data.network.NetworkClient
import com.example.myapplication.core.data.network.NetworkConnector
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@Suppress("NoTrailingSpaces")
val networkModule = module {
    single<Json> {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
            prettyPrint = true
            encodeDefaults = true
        }
    }

    single<HttpClient> {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(get())
            }
        }
    }

    single<NetworkClient> { NetworkClient(get<HttpClient>()) }

    single<NetworkConnector> { NetworkConnector(androidContext()) }

}