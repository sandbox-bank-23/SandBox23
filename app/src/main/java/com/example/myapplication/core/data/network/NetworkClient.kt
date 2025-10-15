package com.example.myapplication.core.data.network

import com.example.myapplication.core.data.dto.PostmanPostResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class NetworkClient(val client: HttpClient) {
    suspend operator fun invoke(data: Response): Response =
        client.post("https://postman-echo.com/post") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }.body<PostmanPostResponse>().json
}