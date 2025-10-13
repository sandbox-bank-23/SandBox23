

package com.example.myapplication.core.data.repo

import com.example.myapplication.core.data.dto.PostmanPostResponse
import com.example.myapplication.core.data.mock.SkyMock
import com.example.myapplication.core.domain.api.SkyRepository
import com.example.myapplication.core.data.network.Response
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class SkyRepositoryImpl(val client: HttpClient, val skyMock: SkyMock) : SkyRepository {
    override suspend fun replenishment(
        toId: Long,
        toType: String,
        value: Long,
        transactionNumber: Long
    ): Response {
        val data = skyMock.getResponse()

        val postmanResponse = client.post("https://postman-echo.com/post") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }.body<PostmanPostResponse>()

        return postmanResponse.json
    }
}