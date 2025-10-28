package com.example.myapplication.debitcards.domain.api

import com.example.myapplication.core.domain.models.Card
import com.example.myapplication.core.domain.models.Result
import com.example.myapplication.debitcards.domain.models.DebitCardResult
import kotlinx.coroutines.flow.Flow

class CreateDebitCardUseCase(private val debitCardsRepository: DebitCardsRepository) {
    suspend fun createDebitCard(
        userId: Long
    ): Flow<DebitCardResult<Card>> = debitCardsRepository.createDebitCard(userId)
}