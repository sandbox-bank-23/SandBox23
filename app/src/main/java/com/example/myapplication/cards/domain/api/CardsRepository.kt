package com.example.myapplication.cards.domain.api

import com.example.myapplication.core.domain.models.Card
import com.example.myapplication.core.domain.models.CardType
import com.example.myapplication.core.domain.models.Result

interface CardsRepository {
    suspend fun getCards(userId: Long): Result<List<Card>>
    suspend fun closeCard(cardId: Long, type: CardType): Result<Unit>
}