package com.example.myapplication.debitcards.domain.api

import com.example.myapplication.core.data.model.Result
import com.example.myapplication.core.domain.models.Card

class CreateDebitCardUseCase(private val debitCardRepository: DebitCardsRepository) {
    suspend fun createDebitCard(
        currentCardNumber: Long,
        requestNumber: Long,
        userId: Long
    ): Result<Card> = debitCardRepository.createDebitCard(currentCardNumber, requestNumber, userId)
}