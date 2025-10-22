package com.example.myapplication.loans.domain.repository

import com.example.myapplication.loans.domain.model.Credit
import com.example.myapplication.loans.domain.model.Pay
import java.math.BigDecimal

interface LoanRepository {
    suspend fun calculate(sum: BigDecimal, period: Long, percent: Int): Pay?
    suspend fun create(loan: Credit)

    suspend fun getLoan(loanId: Long): Credit

    suspend fun close(loanId: Long): Boolean
}