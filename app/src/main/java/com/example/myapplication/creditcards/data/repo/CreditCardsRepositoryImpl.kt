@file:Suppress("MagicNumber")

package com.example.myapplication.creditcards.data.repo

import com.example.myapplication.core.data.db.CardDao
import com.example.myapplication.core.data.db.CardEntity
import com.example.myapplication.core.data.network.NetworkClient
import com.example.myapplication.core.demo.demoFirstName
import com.example.myapplication.core.demo.demoLastName
import com.example.myapplication.core.domain.models.Card
import com.example.myapplication.core.domain.models.Result
import com.example.myapplication.core.utils.ApiCodes
import com.example.myapplication.creditcards.data.mock.CreditCardsMock
import com.example.myapplication.creditcards.data.repo.dto.RequestData
import com.example.myapplication.creditcards.data.repo.dto.ResponseData
import com.example.myapplication.creditcards.domain.api.CreditCardsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import java.math.BigDecimal
import kotlin.random.Random

class CreditCardsRepositoryImpl(
    private val networkClient: NetworkClient,
    private val dao: CardDao,
    private val creditCardsMock: CreditCardsMock,
    private val json: Json = Json
) : CreditCardsRepository {
    private fun map(card: Card): CardEntity {
        return CardEntity(
            id = card.id,
            balance = card.balance,
            userId = card.userId,
            cvv = card.cvv,
            type = card.type,
            endDate = card.endDate,
            owner = card.owner,
            percent = card.percent,
        )
    }

    override suspend fun createCreditCard(
        userId: Long,
        balance: BigDecimal
    ): Flow<Result<Card>> {
        return flow {
            val cards = dao.getUserCardsByType(userId, TYPE)
            val numberCards = cards?.size ?: 0
            val requestedData = RequestData(
                currentCardNumber = numberCards + 1L,
                requestNumber = Random.nextLong(from = 0, Long.MAX_VALUE),
                cardType = TYPE,
                userId = userId,
                owner = "$demoFirstName $demoLastName",
                balance = balance
            )
            val cardJson = json.encodeToString(value = requestedData)
            val response = networkClient(creditCardsMock.createCreditCard(cardJson))
            val json = response.response
            if (json != null) {
                emit(
                    when (response.code) {
                        ApiCodes.CREATED -> {
                            val responseData = Json.decodeFromString<ResponseData>(json)
                            responseData.card?.let { card ->
                                dao.insertCard(map(card))
                                Result.Success(card)
                            } ?: Result.Error(ApiCodes.EMPTY_BODY)
                        }
                        ApiCodes.INVALID_REQUEST,
                        ApiCodes.USER_EXISTS,
                        ApiCodes.NO_RESPONSE -> Result.Error(response.description)

                        else -> Result.Error(ApiCodes.UNKNOWN_ERROR)
                    }
                )
            } else {
                emit(Result.Error(ApiCodes.UNKNOWN_ERROR))
            }
        }
    }

    companion object {
        private const val TYPE = "credit"
    }
}
