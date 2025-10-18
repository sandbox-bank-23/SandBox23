package com.example.myapplication.cards.di

import com.example.myapplication.carddetails.ui.CardDetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val cardsViewModelModule = module {
    viewModel {
        CardDetailsViewModel()
    }
}