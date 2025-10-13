package com.example.myapplication.debitcards.data.repo

import com.example.myapplication.core.data.network.NetworkClient
import com.example.myapplication.core.domain.models.Response
import com.example.myapplication.debitcards.data.mock.DebitCardsMock
import com.example.myapplication.debitcards.domain.api.DebitCardsRepository

class DebitCardsRepositoryImpl(
    private val client: NetworkClient,
    private val debitCardsMock: DebitCardsMock
) :
    DebitCardsRepository {
    override suspend fun openCard(
        currentCardNumber: Long,
        requestNumber: Long,
        userId: Long
    ): Response {
        val data = debitCardsMock.getResponse()

        return client(data)
    }
}
