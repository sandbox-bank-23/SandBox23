@file:Suppress("MagicNumber")

package com.example.myapplication.cards.data.repo

import com.example.myapplication.cards.data.mock.CardsMock
import com.example.myapplication.cards.domain.api.CardsRepository
import com.example.myapplication.core.domain.models.Card
import com.example.myapplication.core.domain.models.CardType
import com.example.myapplication.core.domain.models.Result
import com.example.myapplication.core.utils.ApiCodes
import kotlinx.serialization.json.Json

class CardsRepositoryImpl(
    private val cardsMock: CardsMock,
    private val json: Json = Json
) : CardsRepository {
    override suspend fun getCards(userId: Long): Result<List<Card>> {
        val result = cardsMock.getCards()
        val r: Result<List<Card>> = if (result.code != ApiCodes.SUCCESS) {
            Result.Error(result.description)
        } else if (result.response == null) {
            Result.Error(ApiCodes.EMPTY_BODY)
        } else {
            runCatching {
                Result.Success(json.decodeFromString<List<Card>>(result.response))
            }.getOrElse {
                Result.Error("${ApiCodes.UNKNOWN_ERROR}: ${it.message}")
            }
        }

        return r
    }

    override suspend fun closeCard(cardId: Long, type: CardType): Result<Unit> {
        return Result.Success(Unit)
    }
}
