package com.example.myapplication.loansanddeposits.di.data

import com.example.myapplication.loansanddeposits.data.mock.LoansAndDepositsMock
import com.example.myapplication.loansanddeposits.data.repo.LoansAndDepositsRepositoryImpl
import com.example.myapplication.loansanddeposits.domain.api.LoansAndDepositsRepository
import io.ktor.client.HttpClient
import org.koin.dsl.module

@Suppress("NoTrailingSpaces")
val loansAndDepositsModule = module {
    single<LoansAndDepositsMock> { LoansAndDepositsMock() }

    single<LoansAndDepositsRepository> {
        LoansAndDepositsRepositoryImpl(
            client = get<HttpClient>(),
            loansAndDepositsMock = get<LoansAndDepositsMock>()
        )
    }
}
