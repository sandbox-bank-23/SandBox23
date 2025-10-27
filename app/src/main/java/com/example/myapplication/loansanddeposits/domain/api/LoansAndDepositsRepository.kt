package com.example.myapplication.loansanddeposits.domain.api

import com.example.myapplication.core.domain.models.Product
import com.example.myapplication.core.domain.models.Result

interface LoansAndDepositsRepository {
    suspend fun getAllLoansAndDeposits(userId: Long): Result<List<Product>>
}