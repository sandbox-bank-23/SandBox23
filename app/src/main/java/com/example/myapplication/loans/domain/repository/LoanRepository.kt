package com.example.myapplication.loans.domain.repository

import com.example.myapplication.loans.domain.model.Credit
import com.example.myapplication.loans.domain.model.LoanResult
import com.example.myapplication.loans.domain.model.Pay
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface LoanRepository {
    suspend fun calculate(sum: BigDecimal, period: Long, percent: Int): Pay?
    suspend fun create(loan: Credit): Flow<LoanResult>

    suspend fun getLoan(loanId: Long): Flow<LoanResult>

    suspend fun close(loanId: Long): Flow<LoanResult>
}