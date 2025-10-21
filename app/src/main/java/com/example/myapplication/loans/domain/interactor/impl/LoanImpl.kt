package com.example.myapplication.loans.domain.interactor.impl

import com.example.myapplication.loans.domain.interactor.Loan
import com.example.myapplication.loans.domain.model.Credit
import com.example.myapplication.loans.domain.model.Pay
import com.example.myapplication.loans.domain.repository.LoanRepository
import java.math.BigDecimal

class LoanImpl(
    val repository: LoanRepository
) : Loan {

    override suspend fun calculatePay(loanSum: BigDecimal, period: Long): Pay? {
        return repository.calculate(sum = loanSum, period = period, percent = INTEREST_RATE)
    }

    override suspend fun create(loan: Credit) {
        return repository.create(loan = loan)
    }

    override suspend fun close(loanId: Long): Boolean {
        return repository.close(loanId = loanId)
    }

    override suspend fun getLoan(loanId: Long): Credit {
        TODO("Not yet implemented")
    }

    companion object {
        const val INTEREST_RATE = 25
    }
}