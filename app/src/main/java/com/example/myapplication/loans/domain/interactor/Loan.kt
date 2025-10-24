package com.example.myapplication.loans.domain.interactor

import com.example.myapplication.loans.domain.model.Credit
import com.example.myapplication.loans.domain.model.LoanResult
import com.example.myapplication.loans.domain.model.Pay
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface Loan {
    suspend fun calculatePay(loanSum: BigDecimal, period: Long): Pay?
    suspend fun getLoan(loanId: Long): Flow<LoanResult>
    suspend fun getLoanList(userId: Long): Flow<List<Credit>>
    suspend fun create(loan: Credit): Flow<LoanResult>
    suspend fun close(loanId: Long): Flow<LoanResult>
}