package com.example.myapplication.creditcards.domain.api

import com.example.myapplication.core.domain.models.Response

interface CreditCardsRepository {
    suspend fun openCard(currentCardNumber: Long, requestNumber: Long, userId: Long): Response
}