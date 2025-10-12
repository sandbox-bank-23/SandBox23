package com.example.myapplication.deposits.data.repo

import com.example.myapplication.core.data.dto.PostmanPostResponse
import com.example.myapplication.core.domain.models.Response
import com.example.myapplication.deposits.data.mock.DepositMock
import com.example.myapplication.deposits.domain.api.DepositsRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class DepositRepositoryImpl(val client: HttpClient, val depositMock: DepositMock) : DepositsRepository {
    override suspend fun openDeposit(
        currentDepositNumber: Long,
        requestNumber: Long,
        userId: Long,
        percentType: Long,
        period: Long
    ): Response {
        val data = depositMock.getResponse()

        val postmanResponse = client.post("https://postman-echo.com/post") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }.body<PostmanPostResponse>()

        return postmanResponse.json
    }
}
