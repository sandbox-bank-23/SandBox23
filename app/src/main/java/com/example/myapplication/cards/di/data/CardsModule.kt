package com.example.myapplication.cards.di.data

import com.example.myapplication.cards.data.mock.CardsMock
import com.example.myapplication.cards.data.repo.CardsRepositoryImpl
import com.example.myapplication.cards.domain.api.CardsRepository
import org.koin.dsl.module

@Suppress("NoTrailingSpaces")
val cardsModule = module {
    single<CardsMock> { CardsMock() }

    single<CardsRepository> {
        CardsRepositoryImpl(
            //client = get<NetworkClient>(),
            cardsMock = get<CardsMock>()
        )
    }
}