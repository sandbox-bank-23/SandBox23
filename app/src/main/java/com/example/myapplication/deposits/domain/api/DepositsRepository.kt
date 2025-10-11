package com.example.myapplication.deposits.domain.api

import com.example.myapplication.core.domain.models.Response

interface DepositsRepository {
    suspend fun openDeposit(
        currentDepositNumber: Long,
        requestNumber: Long,
        userId: Long,
        percentType: Long,
        period: Long
    ): Response
}