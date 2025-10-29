package com.example.myapplication.deposits.di.viewmodelModule

import com.example.myapplication.deposits.ui.viewmodel.DepositDetailViewModel
import com.example.myapplication.deposits.ui.viewmodel.NewDepositViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { NewDepositViewModel(get(), get()) }

    viewModel { DepositDetailViewModel(get(), get(), get()) }
}