package com.example.myapplication.deposits.domain.api

import com.example.myapplication.core.domain.models.Result
import com.example.myapplication.deposits.domain.entity.Deposit

interface DepositsRepository {

    suspend fun openDeposit(
        currentDepositNumber: Long,
        requestNumber: Long,
        userId: Long,
        percentType: Long,
        period: Long
    ): Result<Deposit>

    suspend fun closeDeposit(
        depositNumber: Long,
        requestNumber: Long,
        userId: Long,
    ): Result<Deposit>

    suspend fun getProducts(): Result<List<Deposit>>

    suspend fun getDeposits(): List<Deposit>
}
