package com.example.myapplication.creditcards.domain.api

class CheckCreditCardCountUseCase(private val creditCardsRepository: CreditCardsRepository) {
    suspend fun isCardCountLimit(userId: Long, countLimit: Int): Boolean {
        return creditCardsRepository.isCardCountLimit(userId, countLimit)
    }
}