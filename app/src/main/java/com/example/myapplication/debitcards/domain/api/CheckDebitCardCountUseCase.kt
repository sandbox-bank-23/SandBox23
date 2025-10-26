package com.example.myapplication.debitcards.domain.api

class CheckDebitCardCountUseCase(private val debitCardsRepository: DebitCardsRepository) {
    suspend fun isCardCountLimit(userId: Long, countLimit: Int): Boolean {
        return debitCardsRepository.isCardCountLimit(userId, countLimit)
    }
}