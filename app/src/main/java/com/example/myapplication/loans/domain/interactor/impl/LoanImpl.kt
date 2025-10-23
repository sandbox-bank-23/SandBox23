package com.example.myapplication.loans.domain.interactor.impl

import com.example.myapplication.loans.domain.interactor.Loan
import com.example.myapplication.loans.domain.model.Credit
import com.example.myapplication.loans.domain.model.LoanResult
import com.example.myapplication.loans.domain.model.Pay
import com.example.myapplication.loans.domain.repository.LoanRepository
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

class LoanImpl(
    val repository: LoanRepository
) : Loan {

    override suspend fun calculatePay(loanSum: BigDecimal, period: Long): Pay? {
        return repository.calculate(sum = loanSum, period = period, percent = INTEREST_RATE)
    }

    override suspend fun create(loan: Credit): Flow<LoanResult> {
        return repository.create(loan = loan)
    }

    override suspend fun close(loanId: Long): Flow<LoanResult> {
        return repository.close(loanId = loanId)
    }

    override suspend fun getLoan(loanId: Long): Flow<LoanResult> {
        return repository.getLoan(loanId = loanId)
    }

    companion object {
        const val INTEREST_RATE = 25
    }
}