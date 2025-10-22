package com.example.myapplication.deposits.domain.usecase.impl

import com.example.myapplication.core.domain.models.Result
import com.example.myapplication.deposits.domain.api.DepositsRepository
import com.example.myapplication.deposits.domain.entity.Deposit
import com.example.myapplication.deposits.domain.usecase.inter.GetProductsUseCase

class GetProductsUseCaseImpl(
    private val repository: DepositsRepository
) : GetProductsUseCase {
    override suspend operator fun invoke(): Result<List<Deposit>> {
        return repository.getProducts()
    }
}
