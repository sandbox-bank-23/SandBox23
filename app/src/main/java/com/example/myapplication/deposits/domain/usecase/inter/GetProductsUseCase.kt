package com.example.myapplication.deposits.domain.usecase.inter

import com.example.myapplication.core.domain.models.Result
import com.example.myapplication.deposits.domain.entity.Deposit

interface GetProductsUseCase {
    suspend operator fun invoke(): Result<List<Deposit>>
}