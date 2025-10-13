package com.example.myapplication.transfer.di.data


import com.example.myapplication.transfer.data.mock.TransferMock
import com.example.myapplication.transfer.data.repo.TransferRepositoryImpl
import com.example.myapplication.transfer.domain.api.TransferRepository
import io.ktor.client.HttpClient
import org.koin.dsl.module

val transferModule = module {
    single<TransferMock> { TransferMock() }
    
    single<TransferRepository> {
        TransferRepositoryImpl(
            client = get<HttpClient>(),
            transferMock = get<TransferMock>()
        )
    }
}
