package com.example.myapplication.auth.di.data

import com.example.myapplication.auth.data.mock.AuthMock
import com.example.myapplication.auth.data.repo.AuthRepositoryImpl
import com.example.myapplication.auth.domain.api.AuthRepository
import io.ktor.client.HttpClient
import org.koin.dsl.module

val authDataModule = module {
    single<AuthMock> { AuthMock() }

    single<AuthRepository> {
        AuthRepositoryImpl(
            client = get<HttpClient>(),
            authMock = get<AuthMock>()
        )
    }
}