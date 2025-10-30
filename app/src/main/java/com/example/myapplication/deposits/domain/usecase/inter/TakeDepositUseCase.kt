package com.example.myapplication.deposits.domain.usecase.inter

import com.example.myapplication.deposits.domain.DepositResult
import com.example.myapplication.deposits.domain.entity.Deposit

interface TakeDepositUseCase {

    suspend operator fun invoke(id: Long): DepositResult<Deposit>
}