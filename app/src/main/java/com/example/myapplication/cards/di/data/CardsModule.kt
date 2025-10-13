package com.example.myapplication.cards.di.data

import com.example.myapplication.cards.data.mock.CardsMock
import com.example.myapplication.cards.data.repo.CardsRepositoryImpl
import com.example.myapplication.cards.domain.api.CardsRepository
import io.ktor.client.HttpClient
import org.koin.dsl.module

val cardsModule = module {
    single<CardsMock> { CardsMock() }

    single<CardsRepository> {
        CardsRepositoryImpl(
            client = get<HttpClient>(),
            cardsMock = get<CardsMock>()
        )
    }
}