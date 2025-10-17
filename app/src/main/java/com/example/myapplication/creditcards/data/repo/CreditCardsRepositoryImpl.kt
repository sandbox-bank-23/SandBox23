@file:Suppress("MagicNumber")

package com.example.myapplication.creditcards.data.repo

import com.example.myapplication.core.domain.models.Card
import com.example.myapplication.core.domain.models.Result
import com.example.myapplication.core.utils.ApiCodes
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
            ApiCodes.CREATED -> {
                val body = result.response ?: return Result.Error(ApiCodes.EMPTY_BODY)
                val card = json.decodeFromString<Card>(body)
                Result.Success(card)
            }

            ApiCodes.INVALID_REQUEST,
            ApiCodes.USER_EXISTS,
            ApiCodes.NO_RESPONSE -> Result.Error(result.description)

            else -> Result.Error(ApiCodes.UNKNOWN_ERROR)
        }
    }
}
