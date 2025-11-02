package com.example.myapplication.deposits.domain.usecase.impl

import com.example.myapplication.deposits.domain.DepositResult
import com.example.myapplication.deposits.domain.api.DepositsRepository
import com.example.myapplication.deposits.domain.entity.Deposit
import com.example.myapplication.deposits.domain.usecase.inter.TakeDepositUseCase

class TakeDepositUseCaseImpl(
    private val depositsRepository: DepositsRepository
) : TakeDepositUseCase {
    override suspend fun invoke(id: Long): DepositResult<Deposit> {
        return depositsRepository.takeDeposit(id)
    }
}