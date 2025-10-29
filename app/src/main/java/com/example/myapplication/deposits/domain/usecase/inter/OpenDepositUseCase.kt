package com.example.myapplication.deposits.domain.usecase.inter

import com.example.myapplication.deposits.domain.DepositResult
import com.example.myapplication.deposits.domain.entity.Deposit

interface OpenDepositUseCase {
    suspend operator fun invoke(
        currentDepositNumber: Long,
        requestNumber: Long,
        userId: Long,
        percentType: Long,
        period: Long
    ): DepositResult<Deposit>
}