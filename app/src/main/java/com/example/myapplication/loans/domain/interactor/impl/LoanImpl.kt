package com.example.myapplication.loans.domain.interactor.impl

import com.example.myapplication.loans.domain.interactor.Loan
import com.example.myapplication.core.domain.models.loan.Pay
import com.example.myapplication.core.domain.models.loan.Percent
import com.example.myapplication.loans.domain.repository.LoanRepository
import java.math.BigDecimal

class LoanImpl(
    val repository: LoanRepository
) : Loan {

    override suspend fun calculatePay(loanSum: BigDecimal, period: Long): Pay? {
        return repository.calculate(sum = loanSum, period = period, percent = INTEREST_RATE)
    }

    override suspend fun getPercent(): Percent? {
        return repository.getPercent()
    }

    companion object {
        const val INTEREST_RATE = 25
    }
}