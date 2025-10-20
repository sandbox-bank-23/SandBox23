package com.example.myapplication.loans.domain.interactor

import com.example.myapplication.loans.domain.model.Pay
import java.math.BigDecimal

interface Loan {
    suspend fun calculatePay(loanSum: BigDecimal, period: Long): Pay?
}