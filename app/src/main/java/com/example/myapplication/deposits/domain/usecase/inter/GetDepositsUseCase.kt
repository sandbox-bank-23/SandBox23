package com.example.myapplication.deposits.domain.usecase.inter

import com.example.myapplication.deposits.domain.entity.Deposit

interface GetDepositsUseCase {
    suspend fun invoke(): List<Deposit>
}