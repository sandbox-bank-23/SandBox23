package com.example.myapplication.loans.di.data

import com.example.myapplication.core.data.network.NetworkClient
import com.example.myapplication.loans.data.mock.LoansMock
import com.example.myapplication.loans.data.repo.LoansRepositoryImpl
import com.example.myapplication.loans.domain.api.LoansRepository
import org.koin.dsl.module

@Suppress("NoTrailingSpaces")
val loansModule = module {
    single<LoansMock> { LoansMock() }

    single<LoansRepository> {
        LoansRepositoryImpl(
            client = get<NetworkClient>(),
            loansMock = get<LoansMock>()
        )
    }
}
