package com.example.myapplication.deposits.data.repo

import com.example.myapplication.core.data.network.NetworkClient
import com.example.myapplication.core.data.network.Response
import com.example.myapplication.deposits.data.mock.DepositMock
import com.example.myapplication.deposits.domain.api.DepositsRepository

class DepositRepositoryImpl(
    private val client: NetworkClient,
    private val depositMock: DepositMock
) : DepositsRepository {
    override suspend fun openDeposit(
        currentDepositNumber: Long,
        requestNumber: Long,
        userId: Long,
        percentType: Long,
        period: Long
    ): Response {
        val data = depositMock.getResponse()

        return client(data)
    }
}
