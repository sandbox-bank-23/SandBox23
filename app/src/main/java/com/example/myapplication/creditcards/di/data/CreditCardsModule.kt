package com.example.myapplication.creditcards.di.data

import com.example.myapplication.core.data.db.CardDao
import com.example.myapplication.core.data.network.NetworkClient
import com.example.myapplication.core.data.network.NetworkConnector
import com.example.myapplication.creditcards.data.mock.CreditCardsMock
import com.example.myapplication.creditcards.data.repo.CreditCardsRepositoryImpl
import com.example.myapplication.creditcards.domain.api.CheckCreditCardCountUseCase
import com.example.myapplication.creditcards.domain.api.CreateCreditCardUseCase
import com.example.myapplication.creditcards.domain.api.CreditCardsRepository
import com.example.myapplication.creditcards.domain.api.GetCreditCardTermsUseCase
import org.koin.dsl.module

@Suppress("NoTrailingSpaces")
val creditCardsModule = module {
    single<CreditCardsMock> { CreditCardsMock() }

    single<CreditCardsRepository> {
        CreditCardsRepositoryImpl(
            networkClient = get<NetworkClient>(),
            networkConnector = get<NetworkConnector>(),
            dao = get<CardDao>(),
            creditCardsMock = get<CreditCardsMock>(),
            jsonObj = get()
        )
    }

    single<CreateCreditCardUseCase> {
        CreateCreditCardUseCase(
            creditCardsRepository = get()
        )
    }

    single<CheckCreditCardCountUseCase> {
        CheckCreditCardCountUseCase(
            creditCardsRepository = get()
        )
    }

    single<GetCreditCardTermsUseCase> {
        GetCreditCardTermsUseCase(
            creditCardsRepository = get()
        )
    }

}
