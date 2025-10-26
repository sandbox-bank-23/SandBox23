package com.example.myapplication.debitcards.domain.api

class CheckDebitCardCountUseCase(private val debitCardsRepository: DebitCardsRepository) {
    suspend fun isCardCountLimit(userId: Long): Boolean {
        return debitCardsRepository.isCardCountLimit(userId, DEBIT_CARD_COUNT_LIMIT)
    }

    companion object {
        const val DEBIT_CARD_COUNT_LIMIT = 5
    }
}