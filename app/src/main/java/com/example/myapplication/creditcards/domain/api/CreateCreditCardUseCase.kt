package com.example.myapplication.creditcards.domain.api

import com.example.myapplication.core.domain.models.Card
import com.example.myapplication.creditcards.domain.models.CreditCardResult
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

class CreateCreditCardUseCase(
    private val creditCardsRepository: CreditCardsRepository
) {
    suspend fun createCreditCard(
        userId: Long,
        balance: BigDecimal
    ): Flow<CreditCardResult<Card>> =
        creditCardsRepository.createCreditCard(userId, balance)
}