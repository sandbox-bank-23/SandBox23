
package com.example.myapplication.core.data.repo

import com.example.myapplication.core.data.mock.SkyMock
import com.example.myapplication.core.data.network.NetworkClient
import com.example.myapplication.core.domain.api.SkyRepository
import com.example.myapplication.core.domain.models.Result

class SkyRepositoryImpl(val client: NetworkClient, val skyMock: SkyMock) : SkyRepository {
    override suspend fun replenishment(
        toId: Long,
        toType: String,
        value: Long,
        transactionNumber: Long
    ): Result<Long> {
        val data = client(skyMock.getResponse())

        return when (data.code) {
            TRANSACTION_SUCCESS -> {
                Result.Success(transactionNumber)
            }

            INVALID_TRANSACTION, INVALID_TOKEN, TRANSACTION_DUPLICATE -> {
                Result.Error(data.description)
            }

            else -> {
                Result.Error(UNKNOWN_ERROR)
            }
        }
    }

    companion object {
        private const val TRANSACTION_SUCCESS = 200
        private const val INVALID_TRANSACTION = 400
        private const val INVALID_TOKEN = 403
        private const val TRANSACTION_DUPLICATE = 409
        private const val UNKNOWN_ERROR = "Unknown error"
    }
}