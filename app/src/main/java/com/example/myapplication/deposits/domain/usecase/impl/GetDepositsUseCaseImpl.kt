package com.example.myapplication.deposits.domain.usecase.impl

import com.example.myapplication.deposits.domain.api.DepositsRepository
import com.example.myapplication.deposits.domain.entity.Deposit
import com.example.myapplication.deposits.domain.usecase.inter.GetDepositsUseCase

class GetDepositsUseCaseImpl(
    private val repository: DepositsRepository
) : GetDepositsUseCase {
    override suspend fun invoke(): List<Deposit> {
        return repository.getDeposits()
    }
}
