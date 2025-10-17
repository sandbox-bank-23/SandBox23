package com.example.myapplication.creditcards.di.data

import com.example.myapplication.creditcards.data.mock.CreditCardsMock
import com.example.myapplication.creditcards.data.repo.CreditCardsRepositoryImpl
import com.example.myapplication.creditcards.domain.api.CreditCardsRepository
import org.koin.dsl.module

@Suppress("NoTrailingSpaces")
val creditCardsModule = module {
    single<CreditCardsMock> { CreditCardsMock() }

    single<CreditCardsRepository> {
        CreditCardsRepositoryImpl(
            // client = get<NetworkClient>(),
            creditCardsMock = get<CreditCardsMock>()
        )
    }
}
