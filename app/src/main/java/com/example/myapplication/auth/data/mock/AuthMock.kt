@file:Suppress("MagicNumber")

package com.example.myapplication.auth.data.mock

import com.example.myapplication.core.domain.models.Response
import java.util.Base64
import kotlin.random.Random

class AuthMock {
    fun getLogin(): Response = when (Random.nextInt(100)) {
        in 0..79 -> loginSuccess()
        in 80..89 -> invalidEmail()
        in 90..99 -> invalidPassword()
        else -> responseNo()
    }

    fun getRegister(): Response = when (Random.nextInt(1, 100)) {
        in 1..85 -> registerSuccess()
        in 86..90 -> userExists()
        in 91..95 -> invalidEmail()
        in 96..100 -> invalidPassword()
        else -> responseNo()
    }

    private fun loginSuccess(): Response = Response(
        code = 200,
        description = "OK",
        response = """
            "access_token": ${formToken()}
            "refresh_token": ${formToken()}
            "user_id": ${Random.nextInt()}
            """.trimIndent()
    )

    private fun registerSuccess(): Response = Response(
        code = 201,
        description = "Created",
        response = """
            "access_token": ${formToken()}
            "refresh_token": ${formToken()}
            "user_id": ${Random.nextInt()}
            """.trimIndent()
    )

    private fun invalidEmail(): Response = Response(
        code = 400,
        description = "Invalid email",
        response = null
    )

    private fun invalidPassword(): Response = Response(
        code = 400,
        description = "Invalid password",
        response = null
    )

    private fun userExists(): Response = Response(
        code = 409,
        description = "User exists",
        response = null
    )

    private fun responseNo(): Response = Response(
        code = 420,
        description = "No",
        response = null
    )

    private fun formToken(): String =
        Base64.getEncoder().withoutPadding().encodeToString(Random.Default.nextBytes(32))
}
