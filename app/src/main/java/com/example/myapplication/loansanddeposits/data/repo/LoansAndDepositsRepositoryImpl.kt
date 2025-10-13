package com.example.myapplication.loansanddeposits.data.repo

import com.example.myapplication.core.data.network.NetworkClient
import com.example.myapplication.core.domain.models.Response
import com.example.myapplication.loansanddeposits.data.mock.LoansAndDepositsMock
import com.example.myapplication.loansanddeposits.domain.api.LoansAndDepositsRepository

class LoansAndDepositsRepositoryImpl(
    private val client: NetworkClient,
    private val loansAndDepositsMock: LoansAndDepositsMock
) : LoansAndDepositsRepository {
    override suspend fun getAllLoansAndDeposits(): Response {
        val data = loansAndDepositsMock.getResponse()

        return client(data)
    }
}