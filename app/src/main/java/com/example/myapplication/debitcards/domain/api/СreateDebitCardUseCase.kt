package com.example.myapplication.debitcards.domain.api

import com.example.myapplication.core.domain.models.Card

class CreateDebitCardUseCase(private val debitCardsRepository: DebitCardsRepository) {
    suspend fun createDebitCard(
        currentCardNumber: Long,
        requestNumber: Long,
        userId: Long
    ): Card {
        return debitCardsRepository.createDebitCard(currentCardNumber, requestNumber, userId)
    }
}