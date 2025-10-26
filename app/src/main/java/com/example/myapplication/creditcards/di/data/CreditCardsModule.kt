package com.example.myapplication.creditcards.di.data

import com.example.myapplication.core.data.db.CardDao
import com.example.myapplication.core.data.network.NetworkClient
import com.example.myapplication.creditcards.data.mock.CreditCardsMock
import com.example.myapplication.creditcards.data.repo.CreditCardsRepositoryImpl
import com.example.myapplication.creditcards.domain.api.CreateCreditCardUseCase
import com.example.myapplication.creditcards.domain.api.CreditCardsRepository
import org.koin.dsl.module

@Suppress("NoTrailingSpaces")
val creditCardsModule = module {
    single<CreditCardsMock> { CreditCardsMock() }

    single<CreditCardsRepository> {
        CreditCardsRepositoryImpl(
            networkClient = get<NetworkClient>(),
            dao = get<CardDao>(),
            creditCardsMock = get<CreditCardsMock>()
        )
    }

    single<CreateCreditCardUseCase> {
        CreateCreditCardUseCase(
            creditCardRepository = get()
        )
    }
}
