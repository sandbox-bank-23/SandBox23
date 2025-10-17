@file:Suppress("MagicNumber")

package com.example.myapplication.debitcards.data.repo

import com.example.myapplication.core.domain.models.Card
import com.example.myapplication.core.domain.models.Result
import com.example.myapplication.core.utils.ApiCodes
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
