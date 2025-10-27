package com.example.myapplication.debitcards.di.viewmodel

import com.example.myapplication.debitcards.ui.viewmodel.DebitCardsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val debitCardsViewModelModule = module {
    viewModel {
        DebitCardsViewModel(
            createDebitCardUseCase = get(),
            checkDebitCardCountUseCase = get(),
            getDebitCardTermsUseCase = get()
        )
    }
}