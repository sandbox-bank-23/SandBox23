package com.example.myapplication.loansanddeposits.data.repo

import com.example.myapplication.core.data.dto.PostmanPostResponse
import com.example.myapplication.core.domain.models.Response
import com.example.myapplication.loansanddeposits.data.mock.LoansAndDepositsMock
import com.example.myapplication.loansanddeposits.domain.api.LoansAndDepositsRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class LoansAndDepositsRepositoryImpl(
    val client: HttpClient,
    val loansAndDepositsMock: LoansAndDepositsMock
) : LoansAndDepositsRepository {
    override suspend fun getAllLoansAndDeposits(): Response {
        val data = loansAndDepositsMock.getResponse()

        val postmanResponse = client.post("https://postman-echo.com/post") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }.body<PostmanPostResponse>()

        return postmanResponse.json
    }
}