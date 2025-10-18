package com.example.myapplication.carddetails.di.viewmodel

import com.example.myapplication.carddetails.ui.CardDetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val cardDetailsViewModelModule = module {
    viewModel { params ->
        CardDetailsViewModel(params.get())
    }
}