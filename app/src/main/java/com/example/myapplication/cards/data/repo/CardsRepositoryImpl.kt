package com.example.myapplication.cards.data.repo

import com.example.myapplication.cards.data.mock.CardsMock
import com.example.myapplication.cards.domain.api.CardsRepository
import com.example.myapplication.core.data.network.NetworkClient
import com.example.myapplication.core.data.network.Response
import com.example.myapplication.core.domain.models.Card

class CardsRepositoryImpl(
    private val client: NetworkClient,
    private val cardsMock: CardsMock
) :
    CardsRepository {
    override suspend fun getCards(): Response {
        val data = cardsMock.getResponse()

        return client(data)
    }

    override fun getCardsUseCase(): MutableList<Card> {
        TODO("Not yet implemented")
    }

    override fun createDebitCardUseCase() {
        TODO("Not yet implemented")
    }

    override fun createCreditCardUseCase() {
        TODO("Not yet implemented")
    }

    override fun closeCardUseCase() {
        TODO("Not yet implemented")
    }
}
