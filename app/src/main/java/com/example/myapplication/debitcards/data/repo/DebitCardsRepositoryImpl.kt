package com.example.myapplication.debitcards.data.repo

import com.example.myapplication.core.data.network.NetworkClient
import com.example.myapplication.core.data.network.Response
import com.example.myapplication.core.domain.models.Card
import com.example.myapplication.debitcards.data.mock.DebitCardsMock
import com.example.myapplication.debitcards.domain.api.DebitCardsRepository
import kotlinx.serialization.json.Json

class DebitCardsRepositoryImpl(
    private val client: NetworkClient,
    private val debitCardsMock: DebitCardsMock,
    private val json: Json = Json
) : DebitCardsRepository {
    override suspend fun createDebitCard(
        currentCardNumber: Long,
        requestNumber: Long,
        userId: Long
    ): Card {
        val result = debitCardsMock.createDebitCard(currentCardNumber, requestNumber, userId)

        return when (result.code) {
            201 -> json.decodeFromString<Card>(result.response ?: error("Empty body"))
            400, 403, 409 -> throw IllegalStateException(result.description)
            else -> throw IllegalStateException("Unexpected ${result.code}: ${result.description}")
        }
    }
}
