package com.example.myapplication.debitcards.domain.api

import com.example.myapplication.debitcards.domain.models.DebitCardTerms
import com.example.myapplication.core.domain.models.Result
import kotlinx.coroutines.flow.Flow

class GetDebitCardTermsUseCase(private val debitCardsRepository: DebitCardsRepository) {
    suspend fun getDebitCardTerms(): Flow<Result<DebitCardTerms>> {
        return debitCardsRepository.getDebitCardTerms()
    }
}