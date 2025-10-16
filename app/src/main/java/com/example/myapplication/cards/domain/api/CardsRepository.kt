package com.example.myapplication.cards.domain.api

import com.example.myapplication.core.data.network.Response
import com.example.myapplication.core.domain.models.Card

interface CardsRepository {
    suspend fun getCards(): Response
    fun getCardsUseCase(): MutableList<Card>
    fun closeCardUseCase()
}