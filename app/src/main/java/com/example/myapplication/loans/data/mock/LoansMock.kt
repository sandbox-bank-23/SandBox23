@file:Suppress("MagicNumber", "UnderscoresInNumericLiterals")

package com.example.myapplication.loans.data.mock

import com.example.myapplication.core.domain.models.Product
import com.example.myapplication.core.domain.models.ProductType
import com.example.myapplication.core.data.network.Response
import kotlinx.serialization.json.Json
import kotlin.random.Random

class LoansMock {
    fun getResponse(): Response =
        when (Random.nextInt(1, 100)) {
            in 1..80 -> takeLoan()
            in 81..85 -> invalidNumber()
            in 86..90 -> invalidOrExpiredToken()
            in 91..95 -> loanExists()
            in 96..100 -> invalidBalance()
            else -> Response(
                code = 420,
                description = "No",
                response = null
            )
        }

    fun takeLoan(): Response {
        val product = Product(
            id = Random.nextLong(1, 1000),
            type = ProductType.LOAN,
            percentType = Random.nextLong(1, 5),
            period = listOf(6, 12, 24, 36).random().toLong(),
            percent = Random.nextLong(8, 20),
            balance = Random.nextLong(0, 5000000)
        )

        val jsonProduct = Json.encodeToString(product)

        return Response(
            code = 200,
            description = "OK",
            response = jsonProduct
        )
    }

    fun invalidNumber(): Response = Response(
        code = 400,
        description = "Invalid deposit number",
        response = null
    )

    fun invalidBalance(): Response = Response(
        code = 400,
        description = "Invalid balance",
        response = null
    )

    fun invalidOrExpiredToken(): Response = Response(
        code = 403,
        description = "Token is invalid or expired",
        response = null
    )

    fun loanExists(): Response = Response(
        code = 409,
        description = "Deposit with current number already exists",
        response = null
    )
}