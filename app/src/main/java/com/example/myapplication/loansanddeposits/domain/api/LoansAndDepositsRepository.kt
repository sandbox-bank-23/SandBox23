package com.example.myapplication.loansanddeposits.domain.api

import com.example.myapplication.core.domain.models.Product
import com.example.myapplication.core.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface LoansAndDepositsRepository {
    suspend fun getAllLoansAndDeposits(userId: Long): Flow<Result<List<Product>>>
}