package com.example.myapplication.loansanddeposits.data.repo

import com.example.myapplication.core.data.network.NetworkClient
import com.example.myapplication.core.domain.models.Product
import com.example.myapplication.core.domain.models.Result
import com.example.myapplication.core.utils.ApiCodes
import com.example.myapplication.loansanddeposits.data.mock.LoansAndDepositsMock
import com.example.myapplication.loansanddeposits.domain.api.LoansAndDepositsRepository
import kotlinx.serialization.json.Json

class LoansAndDepositsRepositoryImpl(
    private val client: NetworkClient,
    private val loansAndDepositsMock: LoansAndDepositsMock,
    private val json: Json = Json
) : LoansAndDepositsRepository {

    override suspend fun getAllLoansAndDeposits(): Result<List<Product>> {
        val raw = client(loansAndDepositsMock.getLoansAndDeposits())
        return when (raw.code) {
            ApiCodes.SUCCESS -> {
                val body = raw.response ?: return Result.Error(ApiCodes.EMPTY_BODY)
                val products = json.decodeFromString<List<Product>>(body)
                Result.Success(products)
            }

            ApiCodes.FORBIDDEN,
            ApiCodes.INVALID_REQUEST,
            ApiCodes.NO_RESPONSE -> Result.Error(raw.description)

            else -> Result.Error(ApiCodes.UNKNOWN_ERROR)
        }
    }
}