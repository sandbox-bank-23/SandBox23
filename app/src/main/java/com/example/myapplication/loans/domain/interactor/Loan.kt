package com.example.myapplication.loans.domain.interactor

import com.example.myapplication.core.domain.models.loan.Pay
import com.example.myapplication.core.domain.models.loan.Percent
import java.math.BigDecimal

interface Loan {
    suspend fun calculatePay(loanSum: BigDecimal, period: Long): Pay?
    suspend fun getPercent(): Percent?
}