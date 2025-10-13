package com.example.myapplication.loans.domain.api

import com.example.myapplication.core.data.network.Response

interface LoansRepository {
    suspend fun takeLoan(
        currentCreditNumber: Long,
        requestNumber: Long,
        userId: Long,
        balance: Long,
        period: Long
    ): Response
}