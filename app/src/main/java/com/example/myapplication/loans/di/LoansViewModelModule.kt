package com.example.myapplication.loans.di

import com.example.myapplication.loans.ui.viewmodel.LoansViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val loansViewModelModule = module{
    viewModel { LoansViewModel() }
}