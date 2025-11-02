@file:Suppress("MagicNumber", "UnderscoresInNumericLiterals")

package com.example.myapplication.cards.data.mock

import com.example.myapplication.core.data.db.CardDao
import com.example.myapplication.core.data.db.CardEntity
import com.example.myapplication.core.data.network.Response
import com.example.myapplication.core.domain.models.Card
import kotlinx.serialization.json.Json
import kotlin.random.Random

class CardsMock(
    private val cardDao: CardDao
) {
    suspend fun getResponse(): Response =
        when (Random.nextInt(1, 100)) {
            in 1..80 -> getCards()
            in 86..90 -> invalidOrExpiredToken()
            else -> Response(
                code = 420,
                description = "No",
                response = null
            )
        }

    suspend fun getCards(): Response {
        val cards = cardDao.getAllCards()
        val formatCards = cards.toDomain()
        val jsonCards = Json.encodeToString(formatCards)
        return Response(
            code = 200,
            description = "OK",
            response = jsonCards
        )
    }

    fun invalidOrExpiredToken(): Response = Response(
        code = 403,
        description = "Token is invalid or expired",
        response = null
    )
    private fun List<CardEntity>.toDomain(): List<Card> {
        return this.map { entity ->
            Card(
                id = entity.id,
                balance = entity.balance,
                userId = entity.userId,
                cvv = entity.cvv,
                type = entity.type,
                endDate = entity.endDate,
                owner = entity.owner,
                percent = entity.percent
            )
        }
    }
}
