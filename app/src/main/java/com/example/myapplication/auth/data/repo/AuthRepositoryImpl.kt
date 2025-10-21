package com.example.myapplication.auth.data.repo

import com.example.myapplication.auth.domain.model.AuthData
import com.example.myapplication.auth.domain.repo.AuthRepository
import com.example.myapplication.auth.domain.state.Result
import com.example.myapplication.core.data.network.NetworkClient
import com.example.myapplication.core.data.network.Response
import com.example.myapplication.core.data.network.ResponseCodes.BAD_REQUEST
import com.example.myapplication.core.data.network.ResponseCodes.SUCCESS
import com.example.myapplication.core.data.network.ResponseCodes.NO_RESPONSE
import com.example.myapplication.core.data.network.ResponseCodes.CREATED
import com.example.myapplication.core.data.network.ResponseCodes.UNKNOWN_ERROR
import com.example.myapplication.core.data.network.ResponseCodes.CONFLICT
import com.example.myapplication.core.data.network.ResponseCodes.FORBIDDEN
import com.example.myapplication.core.data.network.ResponseCodes.NOT_FOUND
import java.util.Base64
import kotlin.random.Random

class AuthRepositoryImpl(
    private val client: NetworkClient
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<AuthData> {
        // Клиент работает только с классом Response на вход, поменять в будущем на Request по возможности
        val request = createAuthRequest(email, password)

        val data = client(request)

        return when (data.code) {
            SUCCESS, CREATED -> Result.Success(processSuccessResponse(data.copy(code = SUCCESS, description = "ОК")))
            BAD_REQUEST, NOT_FOUND, FORBIDDEN, CONFLICT, NO_RESPONSE -> Result.Error(failureResponse(data, false).description)
            else -> Result.Error(UNKNOWN_ERROR)
        }
    }

    override suspend fun register(email: String, password: String): Result<AuthData> {
        val request = createAuthRequest(email, password)

        val data = client(request)

        return when (data.code) {
            SUCCESS, CREATED -> Result.Success(processSuccessResponse(data.copy(code = CREATED, description = "Created")))
            BAD_REQUEST, NOT_FOUND, FORBIDDEN, CONFLICT, NO_RESPONSE -> Result.Error(failureResponse(data, true).description)
            else -> Result.Error(UNKNOWN_ERROR)
        }
    }

    private fun createAuthRequest(email: String, password: String): Response {
        return Response(
            code = 0,
            description = "",
            response = """
                "email": "$email"
                "password": "$password"
            """.trimIndent()
        )
    }

    private fun processSuccessResponse(data: Response): AuthData {
        val newResponse = """
        "access_token": "${formToken()}",
        "refresh_token": "${formToken()}",
        "user_id": ${Random.nextInt(1, 1000)}
    """.trimIndent()

        val updated = data.copy(response = newResponse)

        return parseAuthResponse(updated)
    }

    private fun failureResponse(data: Response, isRegister: Boolean): Response {
        // в части авторизации не может быть 403 и 404, но в клиенте обобощил ответы
        // флаг register нужен, чтобы обрабатывать 409 для входа
        var code = data.code
        if (code == CONFLICT && !isRegister) {
            code = BAD_REQUEST
        }
        if (code == FORBIDDEN || code == NOT_FOUND) {
            code = BAD_REQUEST
        }

        val (desc, body) = when (code) {
            400 -> "Invalid email or password" to """{ "error": "Invalid email or password" }"""
            409 -> "User exists" to """{ "error": "User exists" }"""
            420 -> "No response from server" to """{ "error": "No response from server" }"""
            else -> "Unknown error" to """{ "error": "Unknown error" }"""
        }

        return Response(
            code = code,
            description = desc,
            response = body
        )
    }

    private fun formToken(): String =
        Base64.getEncoder().withoutPadding().encodeToString(Random.Default.nextBytes(32))

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
