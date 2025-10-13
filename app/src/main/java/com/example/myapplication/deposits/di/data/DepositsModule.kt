package com.example.myapplication.deposits.di.data

import com.example.myapplication.deposits.data.mock.DepositMock
import com.example.myapplication.deposits.data.repo.DepositRepositoryImpl
import com.example.myapplication.deposits.domain.api.DepositsRepository
import io.ktor.client.HttpClient
import org.koin.dsl.module

@Suppress("NoTrailingSpaces")
val depositsModule = module { 
    single<DepositMock> { DepositMock() }
    
    single<DepositsRepository> {
        DepositRepositoryImpl(
            client = get<HttpClient>(),
            depositMock = get<DepositMock>()
        )
    }
}
