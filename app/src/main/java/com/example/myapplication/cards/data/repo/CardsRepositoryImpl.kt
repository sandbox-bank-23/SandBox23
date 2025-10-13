package com.example.myapplication.cards.data.repo

import com.example.myapplication.cards.domain.api.CardsRepository
import com.example.myapplication.core.data.network.NetworkClient
import com.example.myapplication.core.domain.models.Response
import com.example.myapplication.creditcards.data.mock.CreditCardsMock

class CardsRepositoryImpl(
    private val client: NetworkClient,
    private val cardsMock: CreditCardsMock
) :
    CardsRepository {
    override suspend fun getCards(): Response {
        val data = cardsMock.getResponse()

        return client(data)
    }
}
