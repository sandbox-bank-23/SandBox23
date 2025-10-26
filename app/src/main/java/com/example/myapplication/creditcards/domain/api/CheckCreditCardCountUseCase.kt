package com.example.myapplication.creditcards.domain.api

class CheckCreditCardCountUseCase(private val creditCardsRepository: CreditCardsRepository) {
    suspend fun isCardCountLimit(userId: Long): Boolean {
        return creditCardsRepository.isCardCountLimit(userId, CREDIT_CARD_COUNT_LIMIT)
    }

    companion object {
        const val CREDIT_CARD_COUNT_LIMIT = 5
    }
}