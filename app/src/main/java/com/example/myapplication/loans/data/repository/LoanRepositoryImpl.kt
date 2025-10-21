package com.example.myapplication.loans.data.repository

import com.example.myapplication.core.data.db.dao.LoanDao
import com.example.myapplication.core.data.db.entity.LoanEntity
import com.example.myapplication.core.data.network.NetworkClient
import com.example.myapplication.loans.data.mock.LoansMock
import com.example.myapplication.loans.data.resource.DataResource
import com.example.myapplication.loans.domain.model.Credit
import com.example.myapplication.loans.domain.model.Pay
import com.example.myapplication.loans.domain.repository.LoanRepository
import kotlinx.serialization.json.Json
import java.math.BigDecimal

class LoanRepositoryImpl(
    private val networkClient: NetworkClient,
    private val loansMock: LoansMock,
    private val dao: LoanDao,
    private val dataResource: DataResource,
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
            isClose = credit.isClose
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
            isClose = credit.isClose
        )
    }

    override suspend fun calculate(sum: BigDecimal, period: Long, percent: Int): Pay? {
        return dataResource.calculateLoan(sum = sum, percent = percent, period = period)
    }

    override suspend fun create(loan: Credit) {
        val creditJson = Json.encodeToString(value = loan)
        val response = networkClient(loansMock.createLoan(creditJson))
        response.response?.let { json ->
            val newCredit = Json.decodeFromString<Credit>(string = json)
            dao.create(loan = map(credit = newCredit))
        }
    }

    override suspend fun close(loanId: Long): Boolean {
        var result = false
        val entity = dao.getLoan(loanId = loanId)
        val loan = map(entity)
        val response = networkClient(loansMock.closeLoan(Json.encodeToString(loan)))
        response.response?.let { json ->
            val isClose = Json.decodeFromString<Boolean>(string = json)
            if (isClose) {
                dao.close(entity)
                result = true
            }
        }
        return result
    }

    override suspend fun getLoan(loanId: Long): Credit {
        val entity = dao.getLoan(loanId = loanId)
        return map(credit = entity)
    }
}