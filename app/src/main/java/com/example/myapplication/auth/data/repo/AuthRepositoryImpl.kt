package com.example.myapplication.auth.data.repo

import com.example.myapplication.auth.data.mock.AuthMock
import com.example.myapplication.auth.domain.api.AuthRepository
import com.example.myapplication.core.data.network.NetworkClient
import com.example.myapplication.core.data.network.Response

class AuthRepositoryImpl(val client: NetworkClient, val authMock: AuthMock) : AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): Response {
        val data = authMock.getLogin()

        return client(data)
    }

    override suspend fun register(
        email: String,
        password: String
    ): Response {
        val data = authMock.getRegister()

        return client(data)
    }

}
