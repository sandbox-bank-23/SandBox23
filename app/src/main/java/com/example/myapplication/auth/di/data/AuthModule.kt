package com.example.myapplication.auth.di.data

import com.example.myapplication.auth.data.mock.AuthMock
import com.example.myapplication.auth.data.repo.AuthRepositoryImpl
import com.example.myapplication.auth.domain.repo.AuthRepository
import com.example.myapplication.core.data.db.dao.UserDao
import com.example.myapplication.core.data.network.NetworkClient
import com.example.myapplication.core.domain.api.AppInteractor
import org.koin.dsl.module

val authDataModule = module {
    single<AuthMock> { AuthMock() }

    single<AuthRepository> {
        AuthRepositoryImpl(
            appInteractor = get<AppInteractor>(),
            client = get<NetworkClient>(),
            authMock = get<AuthMock>(),
            dao = get<UserDao>()
        )
    }
}
