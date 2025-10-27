package com.example.myapplication.loansanddeposits.domain

import com.example.myapplication.core.domain.models.Product
import com.example.myapplication.core.domain.models.Result

interface LoansAndDepositsUseCase {
    suspend fun getAllLoansAndDeposits(userId: Long): Result<List<Product>>
}