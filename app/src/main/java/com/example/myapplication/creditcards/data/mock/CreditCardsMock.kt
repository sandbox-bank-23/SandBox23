@file:Suppress("MagicNumber", "UnderscoresInNumericLiterals")

package com.example.myapplication.creditcards.data.mock

import com.example.myapplication.core.data.network.Response
import com.example.myapplication.core.domain.models.CardType
import com.example.myapplication.core.utils.ApiCodes
import com.example.myapplication.core.utils.ApiCodes.CREATED
import com.example.myapplication.core.utils.ApiCodes.INVALID_REQUEST
import com.example.myapplication.creditcards.data.mock.models.Card
import com.example.myapplication.creditcards.data.mock.models.CreditCardTermsDto
import com.example.myapplication.creditcards.data.mock.models.RequestData
import com.example.myapplication.creditcards.data.mock.models.ResponseData
import kotlinx.serialization.json.Json
import kotlin.random.Random

class CreditCardsMock {
    private fun getEndDate() = "${Random.nextInt(1, 13).toString().padStart(2, '0')}/${
        Random.nextInt(
            25,
            32
        )
    }"

    fun getResponse(): Response =
        when (Random.nextInt(1, 100)) {
            in 1..80 -> createCreditCardMock()
            in 81..85 -> invalidNumber()
            in 86..90 -> invalidOrExpiredToken()
            in 91..100 -> cardExists()
            else -> Response(
                code = 420,
                description = "No",
                response = null
            )
        }

    fun createCreditCardMock(): Response {
        val card = Card(
            id = Random.nextLong(1000_0000_0000_0000, 9999_9999_9999_9999),
            cvv = Random.nextLong(100, 1000),
            endDate = getEndDate(),
            owner = listOf("Michael Johnson", "Emily Davis", "Chris Miller").random(),
            type = CardType.CREDIT,
            percent = Random.nextDouble(0.5, 3.0), // Процент для дебетовых карт обычно ниже
            balance = Random.nextLong(0, 1_000_000_00).toBigDecimal(), // Баланс дебетовой карты положительный
            userId = Random.nextLong(1, Long.MAX_VALUE)
        )

        val jsonCard = Json.encodeToString(card)

        return Response(
            code = 201,
            description = "Created",
            response = jsonCard
        )
    }

    fun createCreditCard(json: String): Response {
        var card: Card? = null
        var httpCode: Int
        val requestData = Json.decodeFromString<RequestData>(json)
        val totalNumber = requestData.currentCardNumber
        if (totalNumber > MAX_COUNT) {
            httpCode = INVALID_REQUEST
        } else {
            card = Card(
                id = Random.nextLong(from = 1, until = Long.MAX_VALUE),
                owner = requestData.owner,
                balance = requestData.balance,
                endDate = getEndDate(),
                type = requestData.cardType,
                userId = requestData.userId,
                cvv = (100..999).random().toLong(),
                percent = 0.0,
            )
            httpCode = CREATED
        }

        val response = ResponseData(
            card,
            requestData.requestNumber,
            requestData.currentCardNumber
        )

        return Response(
            code = httpCode,
            description = "OK",
            response = Json.encodeToString(response)
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

    fun getCreditCardTerms(): Response {
        val creditCardTerms = CreditCardTermsDto(
            cashback = CASHBACK,
            maxCount = MAX_COUNT,
            serviceCost = SERVICE_COST,
            maxCreditLimit = MAX_CREDIT_LIMIT
        )
        return Response(
            code = ApiCodes.SUCCESS,
            description = "Credit Card Terms",
            response = Json.encodeToString(creditCardTerms)
        )
    }

    companion object {
        private const val MAX_COUNT = 5
        private const val CASHBACK = 0.3
        private const val SERVICE_COST = 990L
        private const val MAX_CREDIT_LIMIT = 1_000_000L
    }
}
