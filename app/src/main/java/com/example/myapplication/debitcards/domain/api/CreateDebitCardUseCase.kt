package com.example.myapplication.debitcards.domain.api

import com.example.myapplication.core.domain.models.Card
import com.example.myapplication.core.domain.models.Result

class CreateDebitCardUseCase(private val debitCardRepository: DebitCardsRepository) {
    suspend fun createDebitCard(
        currentCardNumber: Long,
        requestNumber: Long,
        userId: Long
    ): Result<Card> = debitCardRepository.createDebitCard(currentCardNumber, requestNumber, userId)
}