package com.example.myapplication.cards.domain.usecases

import com.example.myapplication.cards.domain.api.CardsRepository
import com.example.myapplication.core.data.model.Result
import com.example.myapplication.core.domain.models.CardType

class CloseCardUseCase(private val closeCardRepository: CardsRepository) {
    suspend fun closeCard(cardId: Long, type: CardType): Result<Unit> =
        closeCardRepository.closeCard(cardId, type)
}