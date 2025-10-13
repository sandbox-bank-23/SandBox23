package com.example.myapplication.transfer.data.repo

import com.example.myapplication.core.data.network.NetworkClient
import com.example.myapplication.core.domain.models.Response
import com.example.myapplication.transfer.data.mock.TransferMock
import com.example.myapplication.transfer.domain.api.TransferRepository

class TransferRepositoryImpl(
    private val client: NetworkClient,
    private val transferMock: TransferMock
) : TransferRepository {
    override suspend fun transfer(
        fromId: Long,
        fromType: String,
        toId: Long,
        toType: String,
        transactionNumber: Long,
        userId: Long
    ): Response {
        val data = transferMock.getResponse()

        return client(data)
    }
}
