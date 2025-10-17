@file:Suppress("MagicNumber")
package com.example.myapplication.cards.data.repo

import kotlinx.serialization.json.Json
import com.example.myapplication.cards.data.mock.CardsMock
import com.example.myapplication.cards.domain.api.CardsRepository
import com.example.myapplication.core.domain.models.Card
import com.example.myapplication.core.data.model.Result
import com.example.myapplication.core.domain.models.CardType
class CardsRepositoryImpl(
    private val cardsMock: CardsMock,
    private val json: Json = Json
) : CardsRepository {
    override suspend fun getCards(userId: Long): Result<List<Card>> {
        val result = cardsMock.getCards()
        if (result.code != 200) return Result.Error(result.description)

        val body = result.response ?: return Result.Error("Empty body")
        return runCatching {
            Result.Success(json.decodeFromString<List<Card>>(body))
        }.getOrElse {
            Result.Error("Invalid response format: ${it.message}")
        }
    }

    override suspend fun closeCard(cardId: Long, type: CardType): Result<Unit> {
        return Result.Success(Unit)
    }
}
