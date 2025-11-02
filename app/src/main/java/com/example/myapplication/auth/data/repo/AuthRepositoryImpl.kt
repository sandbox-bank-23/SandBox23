package com.example.myapplication.auth.data.repo

import com.example.myapplication.auth.domain.model.AuthData
import com.example.myapplication.auth.domain.repo.AuthRepository
import com.example.myapplication.core.data.db.dao.UserDao
import com.example.myapplication.core.data.db.entity.UserEntity
import com.example.myapplication.core.data.network.NetworkClient
import com.example.myapplication.core.data.network.Response
import com.example.myapplication.core.data.network.ResponseType
import com.example.myapplication.core.data.network.ResponseTypeMapper
import com.example.myapplication.core.demo.demoFirstName
import com.example.myapplication.core.demo.demoLastName
import com.example.myapplication.core.domain.models.Result
import java.util.Base64
import kotlin.random.Random

@Suppress("MagicNumber")
class AuthRepositoryImpl(
    val client: NetworkClient,
    val dao: UserDao
) : AuthRepository {

    private suspend fun createUser(email: String, authData: AuthData) {
        authData.userId?.let { userId ->
            dao.createUser(
                UserEntity(
                    id = userId.toLong(),
                    firstName = demoFirstName,
                    lastName = demoLastName,
                    email = email
                )
            )
        }
    }

    override suspend fun login(email: String, password: String): Result<AuthData> {
        val request = createAuthRequest(email, password)
        val data = client(request)
        val errorType = ResponseTypeMapper(data.code).mapToResponseType()

        return when (errorType) {
            ResponseType.SUCCESS -> Result.Success(processSuccessResponse(data.copy(description = "OK")))
            ResponseType.BAD_REQUEST -> Result.Error("Invalid email or password")
            ResponseType.NO_CONNECTION -> Result.Error("No internet connection")
            ResponseType.SERVER_ERROR -> Result.Error("Server error")
            else -> Result.Error("Unknown error")
        }
    }

    override suspend fun register(email: String, password: String): Result<AuthData> {
        val request = createAuthRequest(email, password)
        val data = client(request)
        val errorType = ResponseTypeMapper(data.code).mapToResponseType()

        return when (errorType) {
            ResponseType.SUCCESS -> {
                val authData = processSuccessResponse(data.copy(description = "Created"))
                createUser(email = email, authData = authData)
                Result.Success(authData)
            }

            ResponseType.ALREADY_EXISTS -> Result.Error("User already exists")
            ResponseType.BAD_REQUEST -> Result.Error("Invalid data")
            ResponseType.NO_CONNECTION -> Result.Error("No internet connection")
            ResponseType.SERVER_ERROR -> Result.Error("Server error")
            else -> Result.Error("Unknown error")
        }
    }

    private fun createAuthRequest(email: String, password: String): Response =
        Response(
            code = 0,
            description = "",
            response = """
                "email": "$email"
                "password": "$password"
            """.trimIndent()
        )

    private fun processSuccessResponse(data: Response): AuthData {
        val newResponse = """
            "access_token": "${formToken()}",
            "refresh_token": "${formToken()}",
            "user_id": ${Random.nextInt(1, 1000)}
        """.trimIndent()
        return parseAuthResponse(data.copy(response = newResponse))
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

    private fun parseFakeResponse(raw: String): Map<String, String> =
        raw.lines()
            .mapNotNull { line ->
                val parts = line.split(":")
                if (parts.size == 2) {
                    parts[0].trim('"', ' ', '\n') to parts[1].trim('"', ' ', '\n')
                } else {
                    null
                }
            }.toMap()
}

