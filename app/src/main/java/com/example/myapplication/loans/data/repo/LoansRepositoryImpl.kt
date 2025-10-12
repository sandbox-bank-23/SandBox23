package com.example.myapplication.loans.data.repo

import com.example.myapplication.core.data.dto.PostmanPostResponse
import com.example.myapplication.core.domain.models.Response
import com.example.myapplication.loans.data.mock.LoansMock
import com.example.myapplication.loans.domain.api.LoansRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class LoansRepositoryImpl(val client: HttpClient, val loansMock: LoansMock) : LoansRepository {
    override suspend fun takeLoan(
        currentCreditNumber: Long,
        requestNumber: Long,
        userId: Long,
        balance: Long,
        period: Long
    ): Response {
        val data = loansMock.getResponse()

        val postmanResponse = client.post("https://postman-echo.com/post") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }.body<PostmanPostResponse>()

        return postmanResponse.json
    }
}