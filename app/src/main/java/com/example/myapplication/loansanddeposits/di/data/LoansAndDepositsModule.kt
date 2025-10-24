package com.example.myapplication.loansanddeposits.di.data

import com.example.myapplication.core.data.network.NetworkClient
import com.example.myapplication.loansanddeposits.data.mock.LoansAndDepositsMock
import com.example.myapplication.loansanddeposits.data.repo.LoansAndDepositsRepositoryImpl
import com.example.myapplication.loansanddeposits.domain.api.LoansAndDepositsRepository
import com.example.myapplication.loansanddeposits.ui.viewmodel.LoansDepositsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

@Suppress("NoTrailingSpaces")
val loansAndDepositsModule = module {
    single<LoansAndDepositsMock> { LoansAndDepositsMock() }

    single<LoansAndDepositsRepository> {
        LoansAndDepositsRepositoryImpl(
            client = get<NetworkClient>(),
            loansAndDepositsMock = get<LoansAndDepositsMock>()
        )
    }

    viewModel { LoansDepositsViewModel(get()) }
}
