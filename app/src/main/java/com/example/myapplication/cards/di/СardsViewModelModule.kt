package com.example.myapplication.cards.di

import com.example.myapplication.cards.ui.CardsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val cardsViewModelModule = module {
    viewModel {
        CardsViewModel(get(), get())
    }
}