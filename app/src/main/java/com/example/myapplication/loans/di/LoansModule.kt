package com.example.myapplication.loans.di

import com.example.myapplication.cards.domain.api.CardsRepository
import com.example.myapplication.core.data.network.NetworkClient
import com.example.myapplication.debitcards.domain.api.DebitCardsRepository
import com.example.myapplication.loans.data.mock.LoansMock
import com.example.myapplication.loans.data.repo.LoansRepositoryImpl
import com.example.myapplication.loans.data.repository.LoanRepositoryImpl
import com.example.myapplication.loans.data.resource.DataResource
import com.example.myapplication.loans.domain.LoansRepository
import com.example.myapplication.loans.domain.interactor.Loan
import com.example.myapplication.loans.domain.interactor.impl.LoanImpl
import com.example.myapplication.loans.domain.repository.LoanRepository
import org.koin.dsl.module

@Suppress("NoTrailingSpaces")
val loansModule = module {
    single<LoansMock> { LoansMock() }

    // From Andre
    single<LoansRepository> {
        LoansRepositoryImpl(
            client = get<NetworkClient>(),
            loansMock = get<LoansMock>(),
            cardsRepository = get<CardsRepository>(),
            debitCardsRepository = get<DebitCardsRepository>()
        )
    }

    // From GH-48
    single<LoanRepository> {
        LoanRepositoryImpl(
            dataResource = get(),
            networkClient = get(),
            dao = get(),
            loansMock = get()
        )
    }
    single<DataResource> { DataResource(mock = get()) }
    single<Loan> { LoanImpl(repository = get()) }
}
