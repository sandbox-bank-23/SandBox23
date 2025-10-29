package com.example.myapplication.debitcards.di.data

import com.example.myapplication.core.data.db.CardDao
import com.example.myapplication.core.data.network.NetworkClient
import com.example.myapplication.debitcards.data.mock.DebitCardsMock
import com.example.myapplication.debitcards.data.repo.DebitCardsRepositoryImpl
import com.example.myapplication.debitcards.domain.api.CheckDebitCardCountUseCase
import com.example.myapplication.debitcards.domain.api.CreateDebitCardUseCase
import com.example.myapplication.debitcards.domain.api.DebitCardsRepository
import com.example.myapplication.debitcards.domain.api.GetDebitCardTermsUseCase
import org.koin.dsl.module

@Suppress("NoTrailingSpaces")
val debitCardsModule = module {
    single<DebitCardsMock> { DebitCardsMock() }

    single<DebitCardsRepository> {
        DebitCardsRepositoryImpl(
            networkClient = get<NetworkClient>(),
            dao = get<CardDao>(),
            debitCardsMock = get<DebitCardsMock>(),
            jsonObj = get()
        )
    }

    single<CreateDebitCardUseCase> {
        CreateDebitCardUseCase(
            debitCardsRepository = get()
        )
    }

    single<CheckDebitCardCountUseCase> {
        CheckDebitCardCountUseCase(
            debitCardsRepository = get()
        )
    }

    single<GetDebitCardTermsUseCase> {
        GetDebitCardTermsUseCase(
            debitCardsRepository = get(),
        )
    }
}
