@file:Suppress("MagicNumber")

package com.example.myapplication.loansanddeposits.data.mock

import com.example.myapplication.core.data.network.Response
import com.example.myapplication.core.domain.models.Product
import com.example.myapplication.core.domain.models.ProductType
import kotlinx.serialization.json.Json
import kotlin.random.Random

class LoansAndDepositsMock {

    fun getLoansAndDeposits(): Response {
        val productCount = Random.nextInt(5, 15)

        val productList = List(productCount) { index ->
            Product(
                id = index.toLong(),
                type = listOf(ProductType.LOAN, ProductType.DEPOSIT).random(),
                percentType = Random.nextLong(1, 4),
                period = listOf(3, 6, 12, 24, 36).random().toLong(),
                percent = Random.nextLong(8, 22),
                balance = Random.nextLong(0, 100_000_000)
            )
        }

        val jsonProducts = Json.encodeToString(productList)

        return Response(
            code = 200,
            description = "OK",
            response = jsonProducts
        )
    }

    fun invalidOrExpiredToken(): Response = Response(
        code = 403,
        description = "Token is invalid or expired",
        response = null
    )
}