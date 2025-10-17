@file:Suppress("MagicNumber")

package com.example.myapplication.cards.data.repo

import com.example.myapplication.cards.data.mock.CardsMock
import com.example.myapplication.cards.domain.api.CardsRepository
import com.example.myapplication.core.data.model.Result
import com.example.myapplication.core.domain.models.Card
import com.example.myapplication.core.domain.models.CardType
import kotlinx.serialization.json.Json

class CardsRepositoryImpl(
    private val cardsMock: CardsMock,
    private val json: Json = Json
) : CardsRepository {
    override suspend fun getCards(userId: Long): Result<List<Card>> {
        val result = cardsMock.getCards()
        val r: Result<List<Card>> = if (result.code != 200) {
            Result.Error(result.description)
        } else if (result.response == null) {
            Result.Error("Empty body")
        } else {
            runCatching {
                Result.Success(json.decodeFromString<List<Card>>(result.response))
            }.getOrElse {
                Result.Error("Invalid response format: ${it.message}")
            }
        }

        return r
    }

    override suspend fun closeCard(cardId: Long, type: CardType): Result<Unit> {
        return Result.Success(Unit)
    }
}
