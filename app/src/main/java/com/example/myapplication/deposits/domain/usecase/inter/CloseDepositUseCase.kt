package com.example.myapplication.deposits.domain.usecase.inter

import com.example.myapplication.deposits.domain.DepositResult
import com.example.myapplication.deposits.domain.entity.Deposit

interface CloseDepositUseCase {
    suspend operator fun invoke(
        depositNumber: Long,
        requestNumber: Long,
        userId: Long
    ): DepositResult<Deposit>
}