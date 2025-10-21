package com.example.myapplication.auth.data.repo

import com.example.myapplication.auth.domain.model.AuthData
import com.example.myapplication.auth.domain.repo.AuthRepository
import com.example.myapplication.auth.domain.state.Result
import com.example.myapplication.core.data.network.NetworkClient
import com.example.myapplication.core.data.network.Response
import com.example.myapplication.core.data.network.ResponseCodes.INVALID_REQUEST
import com.example.myapplication.core.data.network.ResponseCodes.LOGIN_SUCCESS
import com.example.myapplication.core.data.network.ResponseCodes.NO_RESPONSE
import com.example.myapplication.core.data.network.ResponseCodes.REGISTER_SUCCESS
import com.example.myapplication.core.data.network.ResponseCodes.UNKNOWN_ERROR
import com.example.myapplication.core.data.network.ResponseCodes.USER_EXISTS

class AuthRepositoryImpl(
    private val client: NetworkClient
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<AuthData> {
        val request = Response(
            code = 0,
            description = "",
            response = """
                "email": "$email"
                "password": "$password"
            """.trimIndent()
        )

        val data = client(request)

        return when (data.code) {
            LOGIN_SUCCESS -> Result.Success(parseAuthResponse(data))
            INVALID_REQUEST, NO_RESPONSE -> Result.Error(data.description)
            else -> Result.Error(UNKNOWN_ERROR)
        }
    }

    override suspend fun register(email: String, password: String): Result<AuthData> {
        val request = Response(
            code = 0,
            description = "",
            response = """
                "email": "$email"
                "password": "$password"
            """.trimIndent()
        )

        val data = client(request)

        return when (data.code) {
            REGISTER_SUCCESS, USER_EXISTS -> Result.Success(parseAuthResponse(data))
            INVALID_REQUEST, NO_RESPONSE -> Result.Error(data.description)
            else -> Result.Error(UNKNOWN_ERROR)
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
                } else null
            }.toMap()
    }
}
