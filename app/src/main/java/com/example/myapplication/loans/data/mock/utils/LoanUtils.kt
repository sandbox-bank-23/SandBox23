package com.example.myapplication.loans.data.mock.utils

import java.math.BigDecimal
import java.math.RoundingMode

fun calculatePay(sum: BigDecimal, period: Long, percent: Int): BigDecimal {
    val p = percent.toBigDecimal()
        .divide(
            100.toBigDecimal(),
            2,
            RoundingMode.DOWN
        )
        .divide(
            period.toBigDecimal(),
            2,
            RoundingMode.DOWN
        )
    val one = BigDecimal(1)
    val monthPay = sum.multiply(
        p.add(
            p.divide(
                (one.add(p).pow(period.toInt()) - one),
                10,
                RoundingMode.DOWN
            )
        )
    ).setScale(2, RoundingMode.CEILING)
    return monthPay
}