package com.example.myapplication.debitcards.domain.api

import com.example.myapplication.core.domain.models.Card
import com.example.myapplication.core.data.model.Result
interface DebitCardsRepository {
   suspend fun createDebitCard(
      currentCardNumber: Long,
      requestNumber: Long,
      userId: Long
   ): Result<Card>
}