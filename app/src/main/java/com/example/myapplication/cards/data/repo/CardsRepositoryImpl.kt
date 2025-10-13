@file:Suppress("ImportOrdering")

package com.example.myapplication.cards.data.repo

import com.example.myapplication.cards.data.mock.CardsMock
import com.example.myapplication.cards.domain.api.CardsRepository
import com.example.myapplication.core.domain.models.Response
import com.example.myapplication.core.data.dto.PostmanPostResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class CardsRepositoryImpl(val client: HttpClient, val cardsMock: CardsMock) : CardsRepository {
    override suspend fun getCards(): Response {
        val data = cardsMock.getResponse()

        val postmanResponse = client.post("https://postman-echo.com/post") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }.body<PostmanPostResponse>()

        return postmanResponse.json
    }
}
