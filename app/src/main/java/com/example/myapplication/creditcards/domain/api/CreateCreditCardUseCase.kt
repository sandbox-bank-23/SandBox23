package com.example.myapplication.creditcards.domain.api

import com.example.myapplication.core.domain.models.Card
import com.example.myapplication.core.data.model.Result

class CreateCreditCardUseCase(
    private val creditCardRepository: CreditCardsRepository
) {
    suspend fun createCreditCard(
        currentCardNumber: Long,
        requestNumber: Long,
        userId: Long
    ): Result<Card> =
        creditCardRepository.createCreditCard(currentCardNumber, requestNumber, userId)
}