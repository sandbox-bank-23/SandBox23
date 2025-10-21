package com.example.myapplication.loans.data.mock.model

import com.example.myapplication.utils.BigDecimalAsStringSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class Credit(
    val id: Long,
    val userId: Long,
    val name: String,
    @Serializable(with = BigDecimalAsStringSerializer::class)
    val balance: BigDecimal,
    val period: Long,
    val orderDate: Long,
    val endDate: Long,
    val percent: Long,
    val isClose: Boolean,
    @Serializable(with = BigDecimalAsStringSerializer::class)
    val monthPay: BigDecimal? = null,
)
