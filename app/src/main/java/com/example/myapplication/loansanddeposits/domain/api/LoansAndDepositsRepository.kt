package com.example.myapplication.loansanddeposits.domain.api

import com.example.myapplication.core.domain.models.Response

interface LoansAndDepositsRepository {
    suspend fun getAllLoansAndDeposits(): Response
}