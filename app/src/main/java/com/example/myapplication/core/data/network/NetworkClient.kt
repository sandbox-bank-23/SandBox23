package com.example.myapplication.core.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType

@Suppress("MagicNumber", "LongMethod")
class NetworkClient(private val client: HttpClient) {

    suspend operator fun invoke(data: Response): Response =
        runCatching {
            // успешные запросы чаще
            val code = listOf(
                NetworkParams.SUCCESS_CODE,
                NetworkParams.SUCCESS_CODE,
                NetworkParams.CREATED_CODE,
                NetworkParams.CREATED_CODE,
                NetworkParams.BAD_REQUEST_CODE,
                NetworkParams.FORBIDDEN,
                NetworkParams.NOT_FOUND_CODE,
                NetworkParams.EXISTING_CODE,
                NetworkParams.SERVER_ERROR_CODE
            ).random()

            if (code in 200..299) {
                val response = client.post("https://postman-echo.com/post") {
                    contentType(ContentType.Application.Json)
                    setBody(data)
                }
                val body = response.bodyAsText()

                Response(
                    code = response.status.value,
                    description = response.status.description,
                    response = body
                )
            } else {
                val response = client.get("https://postman-echo.com/status/$code")
                val body = response.bodyAsText()

                Response(
                    code = response.status.value,
                    description = response.status.description,
                    response = body
                )
            }
        }.getOrElse { e ->
            println("Error: ${e.localizedMessage}")
            Response(
                code = NetworkParams.NO_CONNECTION_CODE,
                description = "Нет соединения с интернетом",
                response = null
            )
        }
}