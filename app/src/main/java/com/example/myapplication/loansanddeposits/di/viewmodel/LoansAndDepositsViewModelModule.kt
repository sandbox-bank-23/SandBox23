package com.example.myapplication.loansanddeposits.di.viewmodel

import com.example.myapplication.core.domain.api.AppInteractor
import com.example.myapplication.loansanddeposits.ui.viewmodel.LoansDepositsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val loansAndDepositsViewModelModule = module {
    viewModel {
        LoansDepositsViewModel(
            usecase = get(),
            mapper = get(),
            appInteractor = get<AppInteractor>()
        )
    }
}