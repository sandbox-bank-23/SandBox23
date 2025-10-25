package com.example.myapplication.debitcards.domain.api

import com.example.myapplication.core.domain.models.Card
import com.example.myapplication.core.domain.models.Result
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface DebitCardsRepository {
    suspend fun createDebitCard(
        userId: Long,
        balance: BigDecimal
    ): Flow<Result<Card>>

    suspend fun depositToDebitCard(cardId: Long, amount: BigDecimal): Result<Unit>
}