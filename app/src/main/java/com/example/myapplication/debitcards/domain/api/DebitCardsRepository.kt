package com.example.myapplication.debitcards.domain.api

import com.example.myapplication.core.data.network.Response
import com.example.myapplication.core.domain.models.Card

interface DebitCardsRepository {
   suspend fun createDebitCard(currentCardNumber: Long, requestNumber: Long, userId: Long): Card

}