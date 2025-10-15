package com.example.myapplication.loansanddeposits.domain.api

import com.example.myapplication.core.data.network.Response

interface LoansAndDepositsRepository {
    suspend fun getAllLoansAndDeposits(): Response
}