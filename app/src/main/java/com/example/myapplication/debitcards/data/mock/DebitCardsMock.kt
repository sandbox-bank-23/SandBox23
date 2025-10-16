@file:Suppress("MagicNumber", "UnderscoresInNumericLiterals")

package com.example.myapplication.debitcards.data.mock

import com.example.myapplication.core.data.network.Response
import com.example.myapplication.core.domain.models.Card
import com.example.myapplication.core.domain.models.CardType
import kotlinx.serialization.json.Json
import kotlin.random.Random

class DebitCardsMock(
    private val json: Json = Json
) {
    fun createDebitCard(
        currentCardNumber: Long,
        requestNumber: Long,
        userId: Long
    ): Response {

        if (currentCardNumber !in 1..5) {
            return Response(
                code = 400,
                description = "Некорректный порядковый номер карты (макс 5)",
                response = null
            )
        }

        return when (Random.nextInt(1, 101)) {
            in 1..85 -> {
                val card = Card(
                    id = Random.nextLong(1_0000_0000_0000_0000, 9_9999_9999_9999_9999),
                    cvv = Random.nextLong(100, 1000),
                    endDate = "2028-02-02",
                    owner = listOf("Michael Johnson", "Emily Davis", "Chris Miller").random(),
                    type = CardType.DEBIT,
                    percent = 0.0,
                    balance = 0
                )

                Response(
                    code = 201,
                    description = "Created",
                    response = json.encodeToString(card)
                )
            }

            in 86..90 -> {
                Response(
                    code = 403,
                    description = "Token is invalid or expired",
                    response = null
                )
            }

            in 91..100 -> {
                Response(
                    code = 409,
                    description = "Карта с текущим порядковым номером уже открыта",
                    response = null
                )
            }

            else -> Response(
                code = 420,
                description = "Unexpected mock error",
                response = null
            )
        }
    }
}
