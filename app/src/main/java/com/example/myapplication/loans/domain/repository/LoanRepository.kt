package com.example.myapplication.loans.domain.repository

import com.example.myapplication.loans.domain.model.Credit
import com.example.myapplication.loans.domain.model.LoanResult
import kotlinx.coroutines.flow.Flow

interface LoanRepository {
    suspend fun create(loan: Credit): Flow<LoanResult>

    suspend fun getLoan(loanId: Long): Flow<LoanResult>

    suspend fun getLoanList(userId: Long): Flow<List<Credit>>

    suspend fun close(loanId: Long): Flow<LoanResult>
}