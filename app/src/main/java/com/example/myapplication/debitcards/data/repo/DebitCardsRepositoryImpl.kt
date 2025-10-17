@file:Suppress("MagicNumber")

package com.example.myapplication.debitcards.data.repo

import com.example.myapplication.core.domain.models.Card
import com.example.myapplication.core.data.model.Result
import com.example.myapplication.debitcards.data.mock.DebitCardsMock
import com.example.myapplication.debitcards.domain.api.DebitCardsRepository
import kotlinx.serialization.json.Json

class DebitCardsRepositoryImpl(
    private val debitCardsMock: DebitCardsMock,
    private val json: Json = Json
) : DebitCardsRepository {
    override suspend fun createDebitCard(
        currentCardNumber: Long,
        requestNumber: Long,
        userId: Long
    ): Result<Card> {
        val result = debitCardsMock.createDebitCard()
        return when (result.code) {
            201 -> {
                val body = result.response ?: return Result.Error("Empty body")
                val card = json.decodeFromString<Card>(body)
                Result.Success(card)
            }

            else -> Result.Error(result.description)
        }
    }
}
