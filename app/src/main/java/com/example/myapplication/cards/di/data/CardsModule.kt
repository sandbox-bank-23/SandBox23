package com.example.myapplication.cards.di.data

import com.example.myapplication.cards.data.mock.CardsMock
import com.example.myapplication.cards.data.repo.CardsRepositoryImpl
import com.example.myapplication.cards.domain.api.CardsRepository
import com.example.myapplication.cards.domain.usecases.GetCardsUseCase
import org.koin.dsl.module

@Suppress("NoTrailingSpaces")
val cardsModule = module {
    single<CardsMock> { CardsMock(get()) }

    single<CardsRepository> {
        CardsRepositoryImpl(
            // client = get<NetworkClient>(),
            cardsMock = get<CardsMock>()
        )
    }
    single<GetCardsUseCase> {
        GetCardsUseCase(
            get()
        )
    }
}