package com.example.myapplication.loans.domain.repository

import com.example.myapplication.loans.domain.model.Pay
import java.math.BigDecimal

interface LoanRepository {
    suspend fun calculate(sum: BigDecimal, period: Long, percent: Int): Pay?
}