package com.example.myapplication.deposits.domain.usecase.impl

import com.example.myapplication.core.domain.models.Result
import com.example.myapplication.deposits.domain.api.DepositsRepository
import com.example.myapplication.deposits.domain.entity.Deposit
import com.example.myapplication.deposits.domain.usecase.inter.CloseDepositUseCase

class CloseDepositUseCaseImpl(
    private val repository: DepositsRepository
) : CloseDepositUseCase {
    override suspend operator fun invoke(
        depositNumber: Long,
        requestNumber: Long,
        userId: Long
    ): Result<Deposit> {
        return repository.closeDeposit(depositNumber, requestNumber, userId)
    }
}
