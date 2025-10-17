package com.example.myapplication.auth.data.repo

import com.example.myapplication.auth.data.mock.AuthMock
import com.example.myapplication.auth.domain.model.AuthData
import com.example.myapplication.auth.domain.repo.AuthRepository
import com.example.myapplication.core.data.network.NetworkClient
import com.example.myapplication.core.data.network.Response
import com.example.myapplication.core.domain.models.Result

class AuthRepositoryImpl(val client: NetworkClient, val authMock: AuthMock) : AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): Result<AuthData> {
        val data = client(authMock.getLogin())

        return when (data.code) {
            LOGIN_SUCCESS -> {
                val parsed = parseAuthResponse(data)
                Result.Success(parsed)
            }

            INVALID_REQUEST, NO_RESPONSE -> {
                Result.Error(data.description)
            }

            else -> {
                Result.Error(UNKNOWN_ERROR)
            }
        }
    }

    override suspend fun register(
        email: String,
        password: String
    ): Result<AuthData> {
        val data = client(authMock.getRegister())

        return when (data.code) {
            REGISTER_SUCCESS, USER_EXISTS -> {
                val parsed = parseAuthResponse(data)
                Result.Success(parsed)
            }

            INVALID_REQUEST, NO_RESPONSE -> {
                Result.Error(data.description)
            }

            else -> {
                Result.Error(UNKNOWN_ERROR)
            }
        }
    }

    private fun parseAuthResponse(response: Response): AuthData {
        val raw = response.response ?: ""
        val map = parseFakeResponse(raw)

        return AuthData(
            code = response.code,
            description = response.description,
            accessToken = map["access_token"],
            refreshToken = map["refresh_token"],
            userId = map["user_id"]
        )
    }

    private fun parseFakeResponse(raw: String): Map<String, String> {
        if (raw.isBlank()) return emptyMap()

        return raw.lines()
            .mapNotNull { line ->
                val parts = line.split(":")
                if (parts.size == 2) {
                    val key = parts[0].trim('"', ' ', '\n')
                    val value = parts[1].trim('"', ' ', '\n')
                    key to value
                } else {
                    null
                }
            }.toMap()
    }

    companion object {
        private const val LOGIN_SUCCESS = 200
        private const val REGISTER_SUCCESS = 201
        private const val INVALID_REQUEST = 400
        private const val USER_EXISTS = 409
        private const val NO_RESPONSE = 420
        private const val UNKNOWN_ERROR = "Unknown error"
    }

}
