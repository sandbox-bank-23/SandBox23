package com.example.myapplication.loansanddeposits.di.domain

import com.example.myapplication.loansanddeposits.domain.LoansAndDepositsUseCase
import com.example.myapplication.loansanddeposits.domain.impl.LoansAndDepositsUseCaseImpl
import org.koin.dsl.module

val loansAndDepositsDomainModule = module {
    single<LoansAndDepositsUseCase> {
        LoansAndDepositsUseCaseImpl(get())
    }
}