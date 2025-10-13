@file:Suppress("MagicNumber")

package com.example.myapplication.auth.data.mock

import com.example.myapplication.core.domain.models.Response
import java.util.Base64
import kotlin.random.Random

class AuthMock {
    fun getLogin(): Response {
        when (Random.nextInt(100)) {
            in 0..79 -> {
                return Response(
                    code = 200,
                    description = "OK",
                    response = "\"access_token\": ${formToken()}\n\"refresh_token\": ${formToken()}\n\"user_id\": ${Random.nextInt()}"
                )
            }

            in 80..89 -> {
                return Response(
                    code = 400,
                    description = "Invalid email",
                    response = null
                )
            }

            in 90..99 -> {
                return Response(
                    code = 400,
                    description = "Invalid password",
                    response = null
                )
            }

            // Данный ответ есть, просто потому что без него студия пишет, что нет return
            else -> {
                return Response(
                    code = 420,
                    description = "No",
                    response = null
                )
            }
        }
    }

    fun getRegister(): Response {
        when (Random.nextInt(1, 100)) {
            in 1..85 -> {
                return Response(
                    code = 201,
                    description = "Created",
                    response = "\"access_token\": ${formToken()}\n\"refresh_token\": ${formToken()}\n\"user_id\": ${Random.nextInt()}"
                )
            }

            in 86..90 -> {
                return Response(
                    code = 409,
                    description = "User exists",
                    response = null
                )
            }

            in 91..95 -> {
                return Response(
                    code = 400,
                    description = "Invalid email",
                    response = null
                )
            }

            in 96..100 -> {
                return Response(
                    code = 400,
                    description = "Invalid password",
                    response = null
                )
            }

            // Данный ответ есть, просто потому что без него студия пишет, что нет return
            else -> {
                return Response(
                    code = 420,
                    description = "No",
                    response = null
                )
            }
        }
    }

    private fun formToken(): String = Base64.getEncoder().withoutPadding().encodeToString(Random.Default.nextBytes(32))
}