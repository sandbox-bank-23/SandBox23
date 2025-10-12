package com.example.myapplication.debitcards.data.repo

import com.example.myapplication.core.data.dto.PostmanPostResponse
import com.example.myapplication.core.domain.models.Response
import com.example.myapplication.debitcards.data.mock.DebitCardsMock
import com.example.myapplication.debitcards.domain.api.DebitCardsRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class DebitCardsRepositoryImpl(val client: HttpClient, val debitCardsMock: DebitCardsMock) : DebitCardsRepository {
    override suspend fun openCard(
        currentCardNumber: Long,
        requestNumber: Long,
        userId: Long
    ): Response {
        val data = debitCardsMock.getResponse()

        val postmanResponse = client.post("https://postman-echo.com/post") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }.body<PostmanPostResponse>()

        return postmanResponse.json
    }
}
