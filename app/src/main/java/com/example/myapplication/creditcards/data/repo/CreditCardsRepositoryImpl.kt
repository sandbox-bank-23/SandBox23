package com.example.myapplication.creditcards.data.repo

import com.example.myapplication.core.data.network.NetworkClient
import com.example.myapplication.core.data.network.Response
import com.example.myapplication.creditcards.data.mock.CreditCardsMock
import com.example.myapplication.creditcards.domain.api.CreditCardsRepository

class CreditCardsRepositoryImpl(
    private val client: NetworkClient,
    private val creditCardsMock: CreditCardsMock
) : CreditCardsRepository {
    override suspend fun openCard(
        currentCardNumber: Long,
        requestNumber: Long,
        userId: Long
    ): Response {
        val data = creditCardsMock.getResponse()

        return client(data)
    }
}
