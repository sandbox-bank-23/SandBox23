package com.example.myapplication.loans.data.repo

import com.example.myapplication.cards.domain.api.CardsRepository
import com.example.myapplication.core.data.network.NetworkClient
import com.example.myapplication.core.data.network.Response
import com.example.myapplication.core.domain.models.CardType
import com.example.myapplication.core.domain.models.Product
import com.example.myapplication.core.domain.models.ProductType
import com.example.myapplication.core.domain.models.Result
import com.example.myapplication.core.utils.ApiCodes
import com.example.myapplication.core.utils.ApiCodes.SUCCESS
import com.example.myapplication.debitcards.domain.api.DebitCardsRepository
import com.example.myapplication.loans.data.mock.LoansMock
import com.example.myapplication.loans.domain.LoansRepository
import kotlinx.serialization.json.Json

class LoansRepositoryImpl(
    private val client: NetworkClient,
    private val loansMock: LoansMock,
    private val cardsRepository: CardsRepository,
    private val debitCardsRepository: DebitCardsRepository,
) :
    LoansRepository {
    override suspend fun takeLoan(
        currentCreditNumber: Long,
        requestNumber: Long,
        userId: Long,
        balance: Long,
        period: Long
    ): Response {
        val data = loansMock.getResponse()

        if (data.code == SUCCESS && !data.response.isNullOrBlank()) {
            runCatching {
                val product = Json.decodeFromString<Product>(data.response!!)
                if (product.type == ProductType.LOAN) {
                    val amount = product.balance ?: 0L
                    if (amount > 0) {
                        topUpRandomDebitCard(userId, amount)
                    }
                }
            }.onFailure {
                return Response(
                    code = ApiCodes.NO_RESPONSE,
                    description = ApiCodes.UNKNOWN_ERROR,
                    response = null
                )
            }
        }
        return client(data)
    }

    private suspend fun topUpRandomDebitCard(userId: Long, amount: Long) {
        if (amount <= 0) return

        when (val cardsRes = cardsRepository.getCards(userId)) {
            is Result.Success -> {
                val target = cardsRes.data
                    .asSequence()
                    .filter { it.type == CardType.DEBIT }
                    .toList()
                    .randomOrNull() ?: return

                when (debitCardsRepository.depositToDebitCard(target.id, amount)) {
                    is Result.Success -> Unit
                    is Result.Error -> {
                    }
                }
            }

            is Result.Error -> {
                return
            }
        }
    }
}