package com.example.myapplication.loans.data.repo

import com.example.myapplication.core.data.network.NetworkClient
import com.example.myapplication.core.data.network.Response
import com.example.myapplication.loans.data.mock.LoansMock
import com.example.myapplication.loans.domain.LoansRepository

class LoansRepositoryImpl(
    private val client: NetworkClient,
    private val loansMock: LoansMock
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

        return client(data)
    }
}