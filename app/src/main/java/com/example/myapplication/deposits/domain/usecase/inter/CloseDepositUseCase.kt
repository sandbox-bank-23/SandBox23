package com.example.myapplication.deposits.domain.usecase.inter

import com.example.myapplication.core.domain.models.Result
import com.example.myapplication.deposits.domain.entity.Deposit

interface CloseDepositUseCase {
    suspend operator fun invoke(
        depositNumber: Long,
        requestNumber: Long,
        userId: Long
    ): Result<Deposit>
}