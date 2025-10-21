package com.example.myapplication.core.data.network

import com.example.myapplication.core.data.dto.PostmanPostResponse
import com.example.myapplication.core.data.network.ResponseCodes.BAD_REQUEST
import com.example.myapplication.core.data.network.ResponseCodes.CONFLICT
import com.example.myapplication.core.data.network.ResponseCodes.CREATED
import com.example.myapplication.core.data.network.ResponseCodes.FORBIDDEN
import com.example.myapplication.core.data.network.ResponseCodes.NOT_FOUND
import com.example.myapplication.core.data.network.ResponseCodes.NO_RESPONSE
import com.example.myapplication.core.data.network.ResponseCodes.SUCCESS
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class NetworkClient(private val client: HttpClient) {

    suspend operator fun invoke(data: Response): Response =
        runCatching {
            val response = client.post("https://postman-echo.com/post") {
                contentType(ContentType.Application.Json)
                setBody(data)
            }
            val body = response.body<PostmanPostResponse>()

            // Успех побольше положил в список для частых успешных запросов по рандомному выбору
            val code = listOf(
                SUCCESS,
                SUCCESS,
                SUCCESS,
                SUCCESS,
                CREATED,
                BAD_REQUEST,
                FORBIDDEN,
                NOT_FOUND,
                CONFLICT,
            ).random()

            Response(
                code = code,
                description = response.status.description,
                response = body.json.toString()
            )
        }.getOrElse { e ->
            println("Error: ${e.localizedMessage}")
            Response(
                code = NO_RESPONSE,
                description = "Остутствует соединение с интернетом",
                response = null
            )
        }
}