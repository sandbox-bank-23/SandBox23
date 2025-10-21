package com.example.myapplication.core.data.network

import com.example.myapplication.core.data.dto.PostmanPostResponse
import com.example.myapplication.core.data.network.ResponseCodes.NO_RESPONSE
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class NetworkClient(private val client: HttpClient) {

    suspend operator fun invoke(data: Response): Response =
        runCatching {
            client.post("https://postman-echo.com/post") {
                contentType(ContentType.Application.Json)
                setBody(data)
            }.body<PostmanPostResponse>().json
        }.getOrElse { e ->
            println("Error: ${e.localizedMessage}")
            Response(
                code = NO_RESPONSE,
                description = "Остутствует соединение с интернетом",
                response = null
            )
        }
}