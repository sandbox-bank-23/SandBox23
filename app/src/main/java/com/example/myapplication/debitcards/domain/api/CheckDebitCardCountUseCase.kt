package com.example.myapplication.debitcards.domain.api

const val DEBIT_CARD_COUNT_LIMIT = 0
class CheckDebitCardCountUseCase(private val debitCardRepository: DebitCardsRepository) {
    suspend fun isCardCountLimit(userId: Long): Boolean {
        return debitCardRepository.isCardCountLimit(userId, DEBIT_CARD_COUNT_LIMIT)
    }
}