package com.example.myapplication.loans.di.data

import com.example.myapplication.loans.data.mock.LoansMock
import com.example.myapplication.loans.data.repo.LoansRepositoryImpl
import com.example.myapplication.loans.domain.api.LoansRepository
import io.ktor.client.HttpClient
import org.koin.dsl.module

val loansModule = module { 
    single<LoansMock> { LoansMock() }
    
    single<LoansRepository> {
        LoansRepositoryImpl(
            client = get<HttpClient>(),
            loansMock = get<LoansMock>()
        )
    }
}
