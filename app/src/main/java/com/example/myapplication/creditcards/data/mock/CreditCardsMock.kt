@file:Suppress("MagicNumber", "UnderscoresInNumericLiterals")

package com.example.myapplication.creditcards.data.mock

import com.example.myapplication.core.data.network.Response
import com.example.myapplication.core.domain.models.Card
import com.example.myapplication.core.domain.models.CardType
import kotlinx.serialization.json.Json
import kotlin.random.Random

class CreditCardsMock {
    fun getResponse(): Response =
        when (Random.nextInt(1, 100)) {
            in 1..80 -> createDebitCard()
            in 81..85 -> invalidNumber()
            in 86..90 -> invalidOrExpiredToken()
            in 91..100 -> cardExists()
            else -> Response(
                code = 420,
                description = "No",
                response = null
            )
        }

    fun createDebitCard(): Response {
        val card = Card(
            id = Random.nextLong(1000_0000_0000_0000, 9999_9999_9999_9999),
            cvv = Random.nextLong(100, 1000),
            endDate = "${Random.nextInt(1, 13).toString().padStart(2, '0')}/${
                Random.nextInt(
                    25,
                    32
                )
            }",
            owner = listOf("Michael Johnson", "Emily Davis", "Chris Miller").random(),
            type = CardType.CREDIT,
            percent = Random.nextDouble(0.5, 3.0), // Процент для дебетовых карт обычно ниже
            balance = Random.nextLong(0, 1_000_000_00) // Баланс дебетовой карты положительный
        )

        val jsonCard = Json.encodeToString(card)

        return Response(
            code = 201,
            description = "Created",
            response = jsonCard
        )
    }

    fun invalidNumber(): Response = Response(
        code = 400,
        description = "Invalid card number",
        response = null
    )

    fun invalidOrExpiredToken(): Response = Response(
        code = 403,
        description = "Token is invalid or expired",
        response = null
    )

    fun cardExists(): Response = Response(
        code = 409,
        description = "Card with current number already exists",
        response = null
    )
}
