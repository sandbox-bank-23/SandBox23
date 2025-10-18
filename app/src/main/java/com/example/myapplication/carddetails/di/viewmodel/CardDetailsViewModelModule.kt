package com.example.myapplication.carddetails.di.viewmodel

import com.example.myapplication.cards.ui.CardsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val cardDetailsViewModelModule = module {
    viewModel {
        CardsViewModel()
    }
}