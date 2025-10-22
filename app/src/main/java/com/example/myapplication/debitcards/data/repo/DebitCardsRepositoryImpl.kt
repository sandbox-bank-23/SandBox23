@file:Suppress("MagicNumber")

package com.example.myapplication.debitcards.data.repo

import com.example.myapplication.core.domain.models.Card
import com.example.myapplication.core.domain.models.Result
import com.example.myapplication.core.utils.ApiCodes
import com.example.myapplication.debitcards.data.mock.DebitCardsMock
import com.example.myapplication.debitcards.domain.api.DebitCardsRepository
import kotlinx.serialization.json.Json
import java.math.BigDecimal

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

    override suspend fun depositToDebitCard(
        cardId: Long,
        amount: BigDecimal
    ): Result<Unit> {
        val response = debitCardsMock.depositToDebitCard(cardId, amount)
        return when (response.code) {
            ApiCodes.SUCCESS -> Result.Success(Unit)
            ApiCodes.INVALID_REQUEST -> Result.Error("Invalid request while depositing to card")
            ApiCodes.NO_RESPONSE -> Result.Error("No response from mock server")
            else -> Result.Error("Unknown error: ${response.description}")
        }
    }
}
