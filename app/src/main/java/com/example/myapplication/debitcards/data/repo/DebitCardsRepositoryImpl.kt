@file:Suppress("MagicNumber", "LongMethod")

package com.example.myapplication.debitcards.data.repo

import com.example.myapplication.core.data.db.CardDao
import com.example.myapplication.core.data.db.CardEntity
import com.example.myapplication.core.data.network.NetworkClient
import com.example.myapplication.core.data.network.NetworkConnector
import com.example.myapplication.core.data.network.NetworkParams
import com.example.myapplication.core.demo.demoFirstName
import com.example.myapplication.core.demo.demoLastName
import com.example.myapplication.core.domain.models.Card
import com.example.myapplication.core.domain.models.Result
import com.example.myapplication.core.utils.ApiCodes
import com.example.myapplication.debitcards.data.mock.DebitCardsMock
import com.example.myapplication.debitcards.data.mock.models.DebitCardTermsDto
import com.example.myapplication.debitcards.data.repo.dto.DebitCardMock
import com.example.myapplication.debitcards.data.repo.dto.RequestData
import com.example.myapplication.debitcards.domain.api.DebitCardsRepository
import com.example.myapplication.debitcards.domain.models.DebitCardResult
import com.example.myapplication.debitcards.domain.models.DebitCardTerms
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import java.math.BigDecimal
import kotlin.random.Random

class DebitCardsRepositoryImpl(
    private val networkClient: NetworkClient,
    private val networkConnector: NetworkConnector,
    private val dao: CardDao,
    private val debitCardsMock: DebitCardsMock,
    private val jsonObj: Json
) : DebitCardsRepository {
    private fun map(card: Card): CardEntity {
        return CardEntity(
            id = card.id,
            balance = card.balance,
            userId = card.userId,
            cvv = card.cvv,
            type = card.type,
            endDate = card.endDate,
            owner = card.owner,
            percent = card.percent
        )
    }

    private fun map(debitCardTermsDto: DebitCardTermsDto): DebitCardTerms {
        return DebitCardTerms(
            cashback = debitCardTermsDto.cashback,
            maxCount = debitCardTermsDto.maxCount,
            serviceCost = debitCardTermsDto.serviceCost,
        )
    }

    private suspend fun getRequestedData(userId: Long): RequestData {
        val cards = dao.getUserCardsByType(userId, TYPE)
        val numberCards = cards?.size ?: 0
        return RequestData(
            currentCardNumber = numberCards + 1L,
            requestNumber = Random.nextLong(from = 0, Long.MAX_VALUE),
            cardType = TYPE,
            userId = userId,
            owner = "$demoFirstName $demoLastName"
        )
    }

    private suspend fun makeJsonToRequest(userId: Long): String {
        val requestedData = getRequestedData(userId)
        return jsonObj.encodeToString(value = requestedData)
    }

    private fun create(userId: Long): Flow<DebitCardResult<Card>> {
        return flow {
            val mockData = debitCardsMock.createDebitCard(makeJsonToRequest(userId))
            val response = networkClient(mockData)
            if (mockData.code == NetworkParams.CREATED_CODE) {
                val mockJson = response.response
                mockJson?.let { jsonData ->
                    when (response.code) {
                        NetworkParams.SUCCESS_CODE,
                        NetworkParams.CREATED_CODE -> {
                            val responseMock = jsonObj.decodeFromString<DebitCardMock>(jsonData)
                            val card = responseMock.data.response.card
                            card?.let { item ->
                                dao.insertCard(map(item))
                                emit(DebitCardResult.Success(item))
                            } ?: DebitCardResult.Error(ApiCodes.EMPTY_BODY)
                        }
                        NetworkParams.BAD_REQUEST_CODE,
                        NetworkParams.FORBIDDEN,
                        NetworkParams.EXISTING_CODE -> {
                            emit(DebitCardResult.Error(response.description))
                        }
                        NetworkParams.NO_CONNECTION_CODE -> {
                            emit(DebitCardResult.NetworkError)
                        }
                    }
                } ?: emit(DebitCardResult.Error(ApiCodes.UNKNOWN_ERROR))
            } else {
                emit(DebitCardResult.LimitError)
            }
        }
    }

    override suspend fun createDebitCard(userId: Long): Flow<DebitCardResult<Card>> {
        return if (networkConnector.isConnected()) {
            create(userId)
        } else {
            flow {
                emit(DebitCardResult.Error(ApiCodes.SERVICE_UNAVAILABLE))
            }
        }
    }

    override suspend fun isCardCountLimit(userId: Long, limit: Int): Boolean {
        val cards = dao.getUserCardsByType(userId, TYPE)
        val numberCards = cards?.size ?: 0
        return numberCards >= limit
    }

    override suspend fun getDebitCardTerms(): Flow<Result<DebitCardTerms>> {
        val responseData = debitCardsMock.getDebitCardTerms().response
        val debitCardTerms = map(
            Json.decodeFromString<DebitCardTermsDto>(responseData!!)
        )
        return flow {
            emit(Result.Success(debitCardTerms))
        }
    }

    override suspend fun depositToDebitCard(
        cardId: Long,
        amount: BigDecimal
    ): Result<Unit> {
        val response = debitCardsMock.depositToDebitCard(cardId, amount)
        return when (response.code) {
            ApiCodes.SUCCESS -> Result.Success(Unit)
            ApiCodes.INVALID_REQUEST -> Result.Error("Invalid request while depositing to card")
            ApiCodes.NO_RESPONSE -> Result.Error("No response from mock server")
            else -> Result.Error("Unknown error: ${response.description}")
        }
    }

    companion object {
        private const val TYPE = "debit"
    }
}
