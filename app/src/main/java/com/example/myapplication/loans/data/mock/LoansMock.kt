@file:Suppress("MagicNumber", "UnderscoresInNumericLiterals")

package com.example.myapplication.loans.data.mock

import com.example.myapplication.core.data.network.Response
import com.example.myapplication.core.domain.models.Product
import com.example.myapplication.core.domain.models.ProductType
import com.example.myapplication.loans.data.mock.model.Credit
import com.example.myapplication.loans.data.mock.model.OuterPay
import com.example.myapplication.loans.data.mock.model.RequestData
import com.example.myapplication.loans.data.mock.model.ResponseData
import com.example.myapplication.loans.data.mock.utils.calculatePay
import kotlinx.serialization.json.Json
import java.math.BigDecimal
import java.util.Calendar
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
        val pay = OuterPay(
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

    private fun getEndDate(start: Long, months: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = start
        calendar.add(Calendar.MONTH, months.toInt())
        return calendar.timeInMillis
    }

    private fun calculateMonthPay(sum: BigDecimal, period: Long, percent: Long): BigDecimal {
        return calculatePay(sum, period, percent.toInt())
    }

    fun createLoan(loanJson: String): Response {
        var credit: Credit? = null
        var httpCode: Int
        val requestData = Json.decodeFromString<RequestData>(loanJson)
        val totalNumber = requestData.currentCreditNumber + 1
        if (totalNumber > MAX_COUNT || requestData.totalDept >= MAX_DEPT) {
            httpCode = 400
        } else {
            val percent = getPercent()
            val period = requestData.period
            credit = Credit(
                id = Random.nextLong(from = 1, until = Long.MAX_VALUE),
                name = requestData.loanName,
                userId = requestData.userId,
                period = period,
                balance = requestData.balance,
                percent = percent,
                isClose = false,
                monthPay = calculateMonthPay(requestData.balance, period, percent),
                orderDate = requestData.orderDate,
                endDate = getEndDate(
                    start = requestData.orderDate,
                    months = period
                )
            )
            httpCode = 201
        }
        val response = ResponseData(
            credit,
            requestData.requestNumber,
            requestData.currentCreditNumber,
        )

        val json = Json.encodeToString(response)

        return Response(
            code = httpCode,
            description = "OK",
            response = json
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

    companion object {
        private const val MAX_COUNT = 3
        private val MAX_DEPT = BigDecimal(5_000_000).multiply(BigDecimal(100))
    }
}