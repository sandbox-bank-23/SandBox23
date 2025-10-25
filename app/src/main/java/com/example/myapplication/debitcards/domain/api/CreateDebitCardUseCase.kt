package com.example.myapplication.debitcards.domain.api

import com.example.myapplication.core.domain.models.Card
import com.example.myapplication.core.domain.models.Result
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

class CreateDebitCardUseCase(private val debitCardRepository: DebitCardsRepository) {
    suspend fun createDebitCard(
        userId: Long,
        balance: BigDecimal
    ): Flow<Result<Card>> = debitCardRepository.createDebitCard(userId, balance)
}