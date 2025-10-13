@file:Suppress("MagicNumber", "UnderscoresInNumericLiterals")

package com.example.myapplication.cards.data.mock

import com.example.myapplication.core.domain.models.Card
import com.example.myapplication.core.domain.models.CardType
import com.example.myapplication.core.domain.models.Response
import kotlinx.serialization.json.Json
import kotlin.random.Random

class CardsMock {
    fun getResponse(): Response =
        when (Random.nextInt(1, 100)) {
            in 1..80 -> getCards()
            in 86..90 -> invalidOrExpiredToken()
            else -> Response(
                code = 420,
                description = "No",
                response = null
            )
        }

    // Тут по идее, в БД лезть надо, раз у нас всё на моках
    fun getCards(): Response {
        val cardCount = Random.nextInt(1, 11)
        val owners = listOf("John Doe", "Jane Smith", "Peter Jones", "Mary Williams", "David Brown")

        val cards = List(cardCount) {
            Card(
                id = it.toLong() + 1,
                cvv = Random.nextLong(100, 1000),
                endDate = "${Random.nextInt(1, 13).toString().padStart(2, '0')}/${Random.nextInt(24, 31)}",
                owner = owners.random(),
                type = listOf(CardType.CREDIT, CardType.DEBIT).random(),
                percent = Random.nextDouble(0.0, 25.0),
                balance = Random.nextLong(0, 1000000)
            )
        }

        val jsonCards = Json.encodeToString(cards)

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
}
