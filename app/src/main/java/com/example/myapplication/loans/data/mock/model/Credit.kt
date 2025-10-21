package com.example.myapplication.loans.data.mock.model

import java.math.BigDecimal

data class Credit(
    val id: Long,
    val userId: Long,
    val name: String,
    val balance: BigDecimal,
    val period: Long,
    val orderDate: Long,
    val endDate: Long,
    val percent: Long,
    val isClose: Boolean,

    val monthPay: BigDecimal? = null,
)
