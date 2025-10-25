package com.example.myapplication.loans.data.repository

import com.example.myapplication.cards.domain.api.CardsRepository
import com.example.myapplication.core.data.db.dao.LoanDao
import com.example.myapplication.core.data.db.entity.LoanEntity
import com.example.myapplication.core.data.network.NetworkClient
import com.example.myapplication.core.domain.models.CardType
import com.example.myapplication.core.domain.models.Result
import com.example.myapplication.debitcards.domain.api.DebitCardsRepository
import com.example.myapplication.loans.data.mock.LoansMock
import com.example.myapplication.loans.data.repository.dto.RequestData
import com.example.myapplication.loans.data.repository.dto.ResponseData
import com.example.myapplication.loans.data.resource.DataResource
import com.example.myapplication.loans.domain.model.Credit
import com.example.myapplication.loans.domain.model.LoanResult
import com.example.myapplication.loans.domain.model.Pay
import com.example.myapplication.loans.domain.repository.LoanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import java.math.BigDecimal
import kotlin.random.Random

class LoanRepositoryImpl(
    private val networkClient: NetworkClient,
    private val loansMock: LoansMock,
    private val dao: LoanDao,
    private val dataResource: DataResource,
    private val cardsRepository: CardsRepository,
    private val debitCardsRepository: DebitCardsRepository,
) : LoanRepository {
    private fun map(credit: LoanEntity): Credit {
        return Credit(
            id = credit.id,
            userId = credit.userId,
            name = credit.type,
            balance = credit.balance,
            period = credit.period,
            orderDate = credit.start,
            endDate = credit.end,
            percent = credit.percent,
            isClose = credit.isClose,
            monthPay = credit.monthPay
        )
    }

    private fun map(credit: Credit): LoanEntity {
        return LoanEntity(
            userId = credit.userId,
            type = credit.name,
            balance = credit.balance,
            period = credit.period,
            start = credit.orderDate,
            end = credit.endDate,
            percent = credit.percent,
            isClose = credit.isClose,
            monthPay = credit.monthPay ?: BigDecimal(0)
        )
    }

    override suspend fun calculate(sum: BigDecimal, period: Long, percent: Int): Pay? {
        return dataResource.calculateLoan(sum = sum, percent = percent, period = period)
    }

    private fun errorResult(error: String, status: String): LoanResult.Error {
        return LoanResult.Error(error, status)
    }

    override suspend fun create(loan: Credit): Flow<LoanResult> {
        return flow {
            val userWithLoans = dao.getUserWithLoans(userId = loan.userId)
            val loans = userWithLoans?.loans?.filter { it.isClose != true } ?: emptyList()
            val totalDept = loans.sumOf { it.balance }
            val currentNumber = loans.size
            val requestData = RequestData(
                userId = loan.userId,
                loanName = loan.name,
                balance = loan.balance,
                period = loan.period,
                orderDate = loan.orderDate,
                currentCreditNumber = currentNumber,
                requestNumber = Random.nextLong(),
                totalDept = totalDept
            )
            val creditJson = Json.encodeToString(value = requestData)
            val response = networkClient(loansMock.createLoan(creditJson))
            val json = response.response
            if (json != null) {
                val responseData = Json.decodeFromString<ResponseData>(string = json)
                val newCredit = responseData.body
                when (response.code) {
                    HTTP_CREATE_SUCCESS -> {
                        newCredit?.let { credit ->
                            topUpRandomDebitCard(credit)
                            dao.create(loan = map(credit = credit))
                            emit(LoanResult.Success(credit, SUCCESS))
                        } ?: emit(errorResult(LOAN_CREATE_ERROR, ERROR))
                    }
                    HTTP_CREATE_ERROR -> emit(errorResult(LOAN_CREATE_ERROR, ERROR))
                    else -> emit(errorResult(SERVER_ERROR, ERROR))
                }
            } else {
                emit(errorResult(SERVER_ERROR, ERROR))
            }
        }
    }

    override suspend fun close(loanId: Long): Flow<LoanResult> {
        return flow {
            dao.getLoan(loanId = loanId).collect { entity ->
                if (entity.isClose == false) {
                    val loan = map(entity)
                    val json = Json.encodeToString<Credit>(loan)
                    val response = networkClient(loansMock.closeLoan(loanJson = json))
                    response.response?.let { json ->
                        val isClose = Json.decodeFromString<Boolean>(string = json)
                        if (isClose) {
                            val updateLoan = dao.getCloseLoan(loanEntity = entity.copy(isClose = true))
                            updateLoan.collect {
                                emit(
                                    value = LoanResult.Success(
                                        data = map(credit = it),
                                        status = SUCCESS
                                    )
                                )
                            }
                        }
                    } ?: emit(
                        value = LoanResult.Error(
                            error = SERVER_ERROR,
                            status = ERROR
                        )
                    )
                } else {
                    emit(
                        value = LoanResult.Error(
                            error = LOAN_CLOSE_ERROR,
                            status = ERROR
                        )
                    )
                }
            }
        }
    }

    override suspend fun getLoan(loanId: Long): Flow<LoanResult> {
        return flow {
            dao.getLoan(loanId = loanId).collect { entity ->
                emit(
                    value = LoanResult.Success(
                        status = SUCCESS,
                        data = map(credit = entity)
                    )
                )
            }
        }
    }

    override suspend fun getLoanList(userId: Long): Flow<List<Credit>> {
        val entityFlow = dao.getLoanList(userId = userId)
        val loanFlow = entityFlow.map { entityList ->
            entityList.map { map(it) }
        }
        return loanFlow
    }

    private suspend fun topUpRandomDebitCard(credit: Credit) {
        val amount = credit.balance
        if (amount > BigDecimal.ZERO) {
            val userId = credit.userId
            val validAmount = amount.takeIf { it > BigDecimal.ZERO } ?: return
            val cardsRes = cardsRepository.getCards(userId)

            val target = when (cardsRes) {
                is Result.Success ->
                    cardsRes.data
                        .asSequence()
                        .filter { it.type == CardType.DEBIT }
                        .toList()
                        .randomOrNull()

                is Result.Error -> null
            } ?: return

            when (debitCardsRepository.depositToDebitCard(target.id, validAmount)) {
                is Result.Success -> Unit
                is Result.Error -> Unit
            }
        }
    }

    companion object {
        private const val HTTP_CREATE_SUCCESS = 201
        private const val HTTP_CREATE_ERROR = 400
        private const val LOAN_CLOSE_ERROR = "Loan closing error"
        private const val LOAN_CREATE_ERROR = "The maximum limit is allowed"
        private const val SERVER_ERROR = "Server error"
        private const val SUCCESS = "success"
        private const val ERROR = "error"
    }
}