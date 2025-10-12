package com.example.myapplication.auth.data.repo

import com.example.myapplication.auth.data.mock.AuthMock
import com.example.myapplication.auth.domain.api.AuthRepository
import com.example.myapplication.core.data.dto.PostmanPostResponse
import com.example.myapplication.core.domain.models.Response
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthRepositoryImpl(val client: HttpClient, val authMock: AuthMock) : AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): Response {
        val data = authMock.getLogin()

        val postmanResponse = client.post("https://postman-echo.com/post") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }.body<PostmanPostResponse>()

        return postmanResponse.json
    }

    override suspend fun register(
        email: String,
        password: String
    ): Response {
        val data = authMock.getRegister()

        val postmanResponse = client.post("https://postman-echo.com/post") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }.body<PostmanPostResponse>()

        return postmanResponse.json
    }

}
