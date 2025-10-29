@file:Suppress("MagicNumber", "LongMethod")

package com.example.myapplication.creditcards.data.repo

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
import com.example.myapplication.creditcards.data.mock.CreditCardsMock
import com.example.myapplication.creditcards.data.mock.models.CreditCardTermsDto
import com.example.myapplication.creditcards.data.repo.dto.CreditCardMock
import com.example.myapplication.creditcards.data.repo.dto.RequestData
import com.example.myapplication.creditcards.domain.api.CreditCardsRepository
import com.example.myapplication.creditcards.domain.models.CreditCardResult
import com.example.myapplication.creditcards.domain.models.CreditCardTerms
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import java.math.BigDecimal
import kotlin.random.Random

class CreditCardsRepositoryImpl(
    private val networkClient: NetworkClient,
    private val networkConnector: NetworkConnector,
    private val dao: CardDao,
    private val creditCardsMock: CreditCardsMock,
    private val jsonObj: Json
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

    private fun map(creditCardTermsDto: CreditCardTermsDto): CreditCardTerms {
        return CreditCardTerms(
            cashback = creditCardTermsDto.cashback,
            maxCount = creditCardTermsDto.maxCount,
            serviceCost = creditCardTermsDto.serviceCost,
            maxCreditLimit = creditCardTermsDto.maxCreditLimit
        )
    }

    private suspend fun getRequestedData(userId: Long, balance: BigDecimal): RequestData {
        val cards = dao.getUserCardsByType(userId, TYPE)
        val numberCards = cards?.size ?: 0
        return RequestData(
            currentCardNumber = numberCards + 1L,
            requestNumber = Random.nextLong(from = 0, Long.MAX_VALUE),
            cardType = TYPE,
            userId = userId,
            owner = "$demoFirstName $demoLastName",
            balance = balance
        )
    }

    private suspend fun makeJsonToRequest(userId: Long, balance: BigDecimal): String {
        val requestedData = getRequestedData(userId, balance)
        return jsonObj.encodeToString(value = requestedData)
    }

    private fun create(userId: Long, balance: BigDecimal): Flow<CreditCardResult<Card>> {
        return flow {
            val mockData = creditCardsMock.createCreditCard(makeJsonToRequest(userId, balance))
            val response = networkClient(mockData)
            if (mockData.code == NetworkParams.CREATED_CODE) {
                val mockJson = response.response
                mockJson?.let { jsonData ->
                    when (response.code) {
                        NetworkParams.SUCCESS_CODE,
                        NetworkParams.CREATED_CODE -> {
                            val responseMock = jsonObj.decodeFromString<CreditCardMock>(jsonData)
                            val card = responseMock.data.response.card
                            card?.let { item ->
                                dao.insertCard(map(item))
                                emit(CreditCardResult.Success(item))
                            } ?: CreditCardResult.LimitError
                        }
                        NetworkParams.BAD_REQUEST_CODE,
                        NetworkParams.FORBIDDEN,
                        NetworkParams.EXISTING_CODE -> {
                            emit(CreditCardResult.Error(response.description))
                        }
                        NetworkParams.NO_CONNECTION_CODE -> emit(CreditCardResult.NetworkError)
                    }
                } ?: emit(CreditCardResult.Error(ApiCodes.UNKNOWN_ERROR))
            } else {
                emit(CreditCardResult.LimitError)
            }
        }
    }

    override suspend fun createCreditCard(
        userId: Long,
        balance: BigDecimal
    ): Flow<CreditCardResult<Card>> {
        return if (networkConnector.isConnected()) {
            create(userId, balance)
        } else {
            flow {
                emit(CreditCardResult.NetworkError)
            }
        }
    }

    override suspend fun isCardCountLimit(userId: Long, limit: Int): Boolean {
        val cards = dao.getUserCardsByType(userId, TYPE)
        val numberCards = cards?.size ?: 0
        return numberCards >= limit
    }

    override suspend fun getCreditCardTerms(): Flow<Result<CreditCardTerms>> {
        val responseData = creditCardsMock.getCreditCardTerms().response
        val creditCardTerms = map(
            Json.decodeFromString<CreditCardTermsDto>(responseData!!)
        )
        return flow {
            emit(Result.Success(creditCardTerms))
        }
    }

    companion object {
        private const val TYPE = "credit"
    }
}
