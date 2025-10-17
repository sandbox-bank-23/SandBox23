package com.example.myapplication.cards.domain.api

import com.example.myapplication.core.data.model.Result
import com.example.myapplication.core.domain.models.Card
import com.example.myapplication.core.domain.models.CardType
interface CardsRepository {
    suspend fun getCards(userId: Long): Result<List<Card>>
    suspend fun closeCard(cardId: Long, type: CardType): Result<Unit>
}