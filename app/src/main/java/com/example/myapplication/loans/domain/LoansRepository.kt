package com.example.myapplication.loans.domain

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