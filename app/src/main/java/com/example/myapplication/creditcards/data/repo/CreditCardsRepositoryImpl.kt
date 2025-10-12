package com.example.myapplication.creditcards.data.repo

import com.example.myapplication.core.data.dto.PostmanPostResponse
import com.example.myapplication.core.domain.models.Response
import com.example.myapplication.creditcards.data.mock.CreditCardsMock
import com.example.myapplication.creditcards.domain.api.CreditCardsRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class CreditCardsRepositoryImpl(val client: HttpClient, val creditCardsMock: CreditCardsMock) :
    CreditCardsRepository {
    override suspend fun openCard(
        currentCardNumber: Long,
        requestNumber: Long,
        userId: Long
    ): Response {
        val data = creditCardsMock.getResponse()

        val postmanResponse = client.post("https://postman-echo.com/post") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }.body<PostmanPostResponse>()

        return postmanResponse.json
    }
}