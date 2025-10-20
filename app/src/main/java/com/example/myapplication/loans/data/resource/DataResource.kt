package com.example.myapplication.loans.data.resource

import com.example.myapplication.loans.data.mock.LoansMock
import com.example.myapplication.core.domain.models.loan.Pay
import com.example.myapplication.core.domain.models.loan.Percent
import com.example.myapplication.loans.data.mock.model.Percent as OuterPercent
import com.example.myapplication.loans.data.mock.model.Pay as OuterPay
import kotlinx.serialization.json.Json
import java.math.BigDecimal

class DataResource(
    val mock: LoansMock
) {
    suspend fun calculateLoan(sum: BigDecimal, period: Long, percent: Int): Pay? {
        val response = mock.calculateLoan(sum = sum, percent = percent, period = period)
        val data = response.response
        var pay: Pay? = null
        if (data != null) {
            val outerPay = Json.decodeFromString<OuterPay>(data)
            pay = Pay(sum = outerPay.sum)
        }
        return pay
    }

    suspend fun getPercent(): Percent? {
        val response = mock.getPercent()
        val data = response.response
        var percent: Percent? = null
        if (data != null) {
            val outerPercent = Json.decodeFromString<OuterPercent>(data)
            percent = Percent(value = outerPercent.value)
        }
        return percent
    }
}