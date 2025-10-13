package com.example.myapplication.transfer.di.data


import com.example.myapplication.core.data.network.NetworkClient
import com.example.myapplication.transfer.data.mock.TransferMock
import com.example.myapplication.transfer.data.repo.TransferRepositoryImpl
import com.example.myapplication.transfer.domain.api.TransferRepository
import org.koin.dsl.module

@Suppress("NoTrailingSpaces")
val transferModule = module {
    single<TransferMock> { TransferMock() }
    
    single<TransferRepository> {
        TransferRepositoryImpl(
            client = get<NetworkClient>(),
            transferMock = get<TransferMock>()
        )
    }
}
