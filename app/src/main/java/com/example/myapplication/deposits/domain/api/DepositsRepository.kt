package com.example.myapplication.deposits.domain.api

import com.example.myapplication.deposits.domain.DepositResult
import com.example.myapplication.deposits.domain.entity.Deposit
import kotlinx.coroutines.flow.Flow

interface DepositsRepository {

    suspend fun openDeposit(
        currentDepositNumber: Long,
        requestNumber: Long,
        userId: Long,
        percentType: Long,
        period: Long
    ): DepositResult<Deposit>

    suspend fun closeDeposit(
        depositNumber: Long,
        requestNumber: Long,
        userId: Long,
    ): DepositResult<Deposit>

    suspend fun takeDeposit(id: Long): DepositResult<Deposit>

    suspend fun getProducts(): DepositResult<List<Deposit>>

    suspend fun getDeposits(): List<Deposit>

    suspend fun observeAllDeposits(): Flow<List<Deposit>>
}
