package com.example.myapplication.creditcards.domain.api

import com.example.myapplication.core.domain.models.Result
import com.example.myapplication.creditcards.domain.models.CreditCardTerms
import kotlinx.coroutines.flow.Flow

class GetCreditCardTermsUseCase(private val creditCardsRepository: CreditCardsRepository) {
    suspend fun getCreditCardTerms(): Flow<Result<CreditCardTerms>> {
        return creditCardsRepository.getCreditCardTerms()
    }
}