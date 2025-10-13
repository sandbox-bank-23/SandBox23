package com.example.myapplication.debitcards.domain.api

import com.example.myapplication.core.data.network.Response

interface DebitCardsRepository {
    suspend fun openCard(currentCardNumber: Long, requestNumber: Long, userId: Long): Response

}