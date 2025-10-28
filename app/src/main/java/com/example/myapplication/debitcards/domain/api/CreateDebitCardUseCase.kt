package com.example.myapplication.debitcards.domain.api

import com.example.myapplication.core.domain.models.Card
import com.example.myapplication.core.domain.models.Result
import kotlinx.coroutines.flow.Flow

class CreateDebitCardUseCase(private val debitCardsRepository: DebitCardsRepository) {
    suspend fun createDebitCard(
        userId: Long
    ): Flow<Result<Card>> = debitCardsRepository.createDebitCard(userId)
}