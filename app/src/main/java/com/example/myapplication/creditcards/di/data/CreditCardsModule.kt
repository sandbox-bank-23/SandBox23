package com.example.myapplication.creditcards.di.data

import com.example.myapplication.creditcards.data.mock.CreditCardsMock
import com.example.myapplication.creditcards.data.repo.CreditCardsRepositoryImpl
import com.example.myapplication.creditcards.domain.api.CreditCardsRepository
import io.ktor.client.HttpClient
import org.koin.dsl.module

val creditCardsModule = module { 
    single<CreditCardsMock> { CreditCardsMock() }
    
    single<CreditCardsRepository> {
        CreditCardsRepositoryImpl(
            client = get<HttpClient>(),
            creditCardsMock = get<CreditCardsMock>()
        )
    }
}
