package com.example.myapplication.auth.data

import com.example.myapplication.auth.domain.model.AuthData
import com.example.myapplication.auth.domain.repo.AuthRepository
import com.example.myapplication.auth.domain.state.Result

class AuthRepositoryImpl(
    val mockNetworkClient: NetworkClient
) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Result<AuthData> {
        val loginResponse = mockNetworkClient.login(email, password)

        when (loginResponse) {
            TODO() -> {}
        }
    }

    override suspend fun register(
        email: String,
        password: String
    ): Result<AuthData> {
        val registerResponse = mockNetworkClient.register(email, password)

        when (registerResponse) {
            TODO() -> {}
        }
    }
}