@file:Suppress("MagicNumber")
package com.example.myapplication.creditcards.data.repo

import com.example.myapplication.core.data.model.Result
import com.example.myapplication.core.domain.models.Card
import com.example.myapplication.creditcards.data.mock.CreditCardsMock
import com.example.myapplication.creditcards.domain.api.CreditCardsRepository
import kotlinx.serialization.json.Json
class CreditCardsRepositoryImpl(
    private val creditCardsMock: CreditCardsMock,
    private val json: Json = Json
) : CreditCardsRepository {
    override suspend fun createCreditCard(
        currentCardNumber: Long,
        requestNumber: Long,
        userId: Long
    ): Result<Card> {
        val result = creditCardsMock.createCreditCard()
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
