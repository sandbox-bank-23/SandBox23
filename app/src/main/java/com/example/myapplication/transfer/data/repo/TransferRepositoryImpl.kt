package com.example.myapplication.transfer.data.repo

import com.example.myapplication.core.data.dto.PostmanPostResponse
import com.example.myapplication.core.domain.models.Response
import com.example.myapplication.transfer.data.mock.TransferMock
import com.example.myapplication.transfer.domain.api.TransferRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class TransferRepositoryImpl(val client: HttpClient, val transferMock: TransferMock) : TransferRepository {
    override suspend fun transfer(
        fromId: Long,
        fromType: String,
        toId: Long,
        toType: String,
        transactionNumber: Long,
        userId: Long
    ): Response {
        val data = transferMock.getResponse()

        val postmanResponse = client.post("https://postman-echo.com/post") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }.body<PostmanPostResponse>()

        return postmanResponse.json
    }

}