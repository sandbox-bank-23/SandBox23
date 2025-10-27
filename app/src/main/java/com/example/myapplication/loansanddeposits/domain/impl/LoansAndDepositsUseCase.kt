package com.example.myapplication.loansanddeposits.domain.impl

import com.example.myapplication.core.domain.models.Product
import com.example.myapplication.core.domain.models.Result
import com.example.myapplication.loansanddeposits.domain.LoansAndDepositsUseCase
import com.example.myapplication.loansanddeposits.domain.api.LoansAndDepositsRepository

class LoansAndDepositsUseCaseImpl(
    val loansAndDepositsRepository: LoansAndDepositsRepository
) : LoansAndDepositsUseCase {
    override suspend fun getAllLoansAndDeposits(): Result<List<Product>> {
        return loansAndDepositsRepository.getAllLoansAndDeposits()
    }
}