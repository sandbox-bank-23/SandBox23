package com.example.myapplication.deposits.domain.usecase.inter

import com.example.myapplication.deposits.domain.DepositResult
import com.example.myapplication.deposits.domain.entity.Deposit

interface GetProductsUseCase {
    suspend operator fun invoke(): DepositResult<List<Deposit>>
}