package com.example.myapplication.loansanddeposits.di.viewmodel

import com.example.myapplication.loansanddeposits.ui.viewmodel.LoansDepositsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val loansAndDepositsViewModelModule = module {
    viewModel { LoansDepositsViewModel(get(), get()) }
}