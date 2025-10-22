package com.example.myapplication.deposits.domain.usecase.impl

import com.example.myapplication.core.domain.models.Result
import com.example.myapplication.deposits.domain.api.DepositsRepository
import com.example.myapplication.deposits.domain.entity.Deposit
import com.example.myapplication.deposits.domain.usecase.inter.OpenDepositUseCase

class OpenDepositUseCaseImpl(
    private val repository: DepositsRepository
) : OpenDepositUseCase {
    override suspend operator fun invoke(
        currentDepositNumber: Long,
        requestNumber: Long,
        userId: Long,
        percentType: Long,
        period: Long
    ): Result<Deposit> {
        return repository.openDeposit(
            currentDepositNumber,
            requestNumber,
            userId,
            percentType,
            period
        )
    }
}
