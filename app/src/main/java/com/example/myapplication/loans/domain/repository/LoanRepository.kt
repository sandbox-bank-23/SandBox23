package com.example.myapplication.loans.domain.repository

import com.example.myapplication.core.domain.models.loan.Pay
import com.example.myapplication.core.domain.models.loan.Percent
import java.math.BigDecimal

interface LoanRepository {
    suspend fun calculate(sum: BigDecimal, period: Long, percent: Int): Pay?
    suspend fun getPercent(): Percent?
}