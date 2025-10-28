package com.example.myapplication.loans.domain.interactor.impl

import com.example.myapplication.loans.domain.interactor.Loan
import com.example.myapplication.loans.domain.model.Credit
import com.example.myapplication.loans.domain.model.LoanResult
import com.example.myapplication.loans.domain.repository.LoanRepository
import kotlinx.coroutines.flow.Flow

class LoanImpl(
    val repository: LoanRepository
) : Loan {

    override suspend fun create(loan: Credit): Flow<LoanResult> {
        return repository.create(loan = loan)
    }

    override suspend fun close(loanId: Long): Flow<LoanResult> {
        return repository.close(loanId = loanId)
    }

    override suspend fun getLoan(loanId: Long): Flow<LoanResult> {
        return repository.getLoan(loanId = loanId)
    }

    override suspend fun getLoanList(userId: Long): Flow<List<Credit>> {
        return repository.getLoanList(userId = userId)
    }
}