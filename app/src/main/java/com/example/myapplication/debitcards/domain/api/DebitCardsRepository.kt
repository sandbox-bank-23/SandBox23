package com.example.myapplication.debitcards.domain.api

import com.example.myapplication.core.domain.models.Response

interface DebitCardsRepository {
    suspend fun openCard(currentCardNumber: Long, requestNumber: Long, userId: Long): Response

}