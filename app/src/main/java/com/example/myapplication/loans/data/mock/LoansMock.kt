@file:Suppress("MagicNumber", "UnderscoresInNumericLiterals")

package com.example.myapplication.loans.data.mock

import com.example.myapplication.core.data.network.Response
import com.example.myapplication.core.domain.models.Product
import com.example.myapplication.core.domain.models.ProductType
import com.example.myapplication.loans.data.mock.model.Credit
import com.example.myapplication.loans.data.mock.model.Pay
import com.example.myapplication.loans.data.mock.utils.calculatePay
import kotlinx.serialization.json.Json
import java.math.BigDecimal
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

    private fun getPercent(): Long {
        return Random.nextLong(15, 25)
    }

    fun getPercentResponse(): Response {
        return Response(
            code = 200,
            description = "OK",
            response = Json.encodeToString(getPercent())
        )
    }

    fun calculateLoan(sum: BigDecimal, period: Long, percent: Int): Response {
        val pay = Pay(
            sum = calculatePay(
                sum = sum,
                period = period,
                percent = percent
            )
        )
        return Response(
            code = 200,
            description = "OK",
            response = Json.encodeToString(value = pay)
        )
    }

    fun takeLoan(): Response {
        val product = Product(
            id = Random.nextLong(1, 1000),
            type = ProductType.LOAN,
            percentType = 2,
            period = listOf(6, 12, 24, 36).random().toLong(),
            percent = getPercent(),
            balance = Random.nextLong(0, 5000000)
        )

        val jsonProduct = Json.encodeToString(product)

        return Response(
            code = 200,
            description = "OK",
            response = jsonProduct
        )
    }

    fun closeLoan(loanJson: String): Response {
        Json.decodeFromString<Credit>(loanJson)
        return Response(
            code = 200,
            description = "OK",
            response = Json.encodeToString(true)
        )
    }

    fun createLoan(loanJson: String): Response {
        //percentType = 2 - ежемесячно
        val loanFromClient = Json.decodeFromString<Credit>(loanJson)

        val credit = Credit(
            id = Random.nextLong(),
            name = loanFromClient.name,
            userId = loanFromClient.userId,
            period = loanFromClient.period,
            balance = loanFromClient.balance,
            percent = loanFromClient.percent,
            isClose = false,
            monthPay = loanFromClient.monthPay,
            orderDate = loanFromClient.orderDate,
            endDate = loanFromClient.endDate
        )

        val jsonProduct = Json.encodeToString(credit)

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
        description = "Loan with current number already exists",
        response = null
    )
}