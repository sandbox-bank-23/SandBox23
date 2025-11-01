package com.example.myapplication.loansanddeposits.di.data

import com.example.myapplication.deposits.domain.api.DepositsRepository
import com.example.myapplication.loans.domain.repository.LoanRepository
import com.example.myapplication.loansanddeposits.data.mock.LoansAndDepositsMock
import com.example.myapplication.loansanddeposits.data.repo.LoansAndDepositsRepositoryImpl
import com.example.myapplication.loansanddeposits.domain.api.LoansAndDepositsRepository
import com.example.myapplication.loansanddeposits.ui.mapper.DefaultLoansDepositsUiMapper
import com.example.myapplication.loansanddeposits.ui.mapper.LoansDepositsUiMapper
import org.koin.dsl.module

@Suppress("NoTrailingSpaces")
val loansAndDepositsModule = module {
    single<LoansAndDepositsMock> { LoansAndDepositsMock() }

    single<LoansAndDepositsRepository> {
        LoansAndDepositsRepositoryImpl(
            loan = get<LoanRepository>(),
            deposits = get<DepositsRepository>(),
        )
    }
    factory<LoansDepositsUiMapper> { DefaultLoansDepositsUiMapper() }
}
