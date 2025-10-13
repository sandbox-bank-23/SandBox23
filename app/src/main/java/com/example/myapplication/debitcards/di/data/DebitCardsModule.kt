package com.example.myapplication.debitcards.di.data

import com.example.myapplication.debitcards.data.mock.DebitCardsMock
import com.example.myapplication.debitcards.data.repo.DebitCardsRepositoryImpl
import com.example.myapplication.debitcards.domain.api.DebitCardsRepository
import io.ktor.client.HttpClient
import org.koin.dsl.module

val debitCardsModule = module { 
    single<DebitCardsMock> { DebitCardsMock() }
    
    single<DebitCardsRepository> {
        DebitCardsRepositoryImpl(
            client = get<HttpClient>(),
            debitCardsMock = get<DebitCardsMock>()
        )
    }
}
