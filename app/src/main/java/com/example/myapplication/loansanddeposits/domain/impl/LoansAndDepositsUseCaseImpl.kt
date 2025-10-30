package com.example.myapplication.loansanddeposits.domain.impl

import com.example.myapplication.core.domain.models.Product
import com.example.myapplication.core.domain.models.Result
import com.example.myapplication.loansanddeposits.domain.LoansAndDepositsUseCase
import com.example.myapplication.loansanddeposits.domain.api.LoansAndDepositsRepository
import kotlinx.coroutines.flow.Flow

class LoansAndDepositsUseCaseImpl(
    val loansAndDepositsRepository: LoansAndDepositsRepository
) : LoansAndDepositsUseCase {
    override suspend fun getAllLoansAndDeposits(userId: Long): Flow<Result<List<Product>>> {
        return loansAndDepositsRepository.getAllLoansAndDeposits(userId)
    }
}