package com.example.myapplication.creditcards.di.viewmodel

import com.example.myapplication.creditcards.ui.viewmodel.CreditCardsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val creditCardsViewModelModule = module {
    viewModel {
        CreditCardsViewModel(
            createCreditCardUseCase = get(),
            checkCreditCardCountUseCase = get(),
            getCreditCardTermsUseCase = get(),
            appInteractor = get()
        )
    }
}