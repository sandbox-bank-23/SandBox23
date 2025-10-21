package com.example.myapplication.deposits.domain.usecase.inter

import com.example.myapplication.core.domain.models.Result
import com.example.myapplication.deposits.domain.entity.Deposit

interface OpenDepositUseCase {
    suspend operator fun invoke(
        currentDepositNumber: Long,
        requestNumber: Long,
        userId: Long,
        percentType: Long,
        period: Long
    ): Result<Deposit>
}