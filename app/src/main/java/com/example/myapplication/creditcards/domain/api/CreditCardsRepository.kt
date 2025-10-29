package com.example.myapplication.creditcards.domain.api

import com.example.myapplication.core.domain.models.Card
import com.example.myapplication.core.domain.models.Result
import com.example.myapplication.creditcards.domain.models.CreditCardResult
import com.example.myapplication.creditcards.domain.models.CreditCardTerms
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface CreditCardsRepository {
    suspend fun createCreditCard(
        userId: Long,
        balance: BigDecimal
    ): Flow<CreditCardResult<Card>>
    suspend fun isCardCountLimit(userId: Long, limit: Int): Boolean
    suspend fun getCreditCardTerms(): Flow<Result<CreditCardTerms>>
}