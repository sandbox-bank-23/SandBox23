package com.example.myapplication.loans.data.repository.dto

import com.example.myapplication.utils.BigDecimalAsStringSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class RequestData(
    val userId: Long,
    val loanName: String,
    @Serializable(with = BigDecimalAsStringSerializer::class)
    val balance: BigDecimal,
    val period: Long,
    val orderDate: Long,
    @Serializable(with = BigDecimalAsStringSerializer::class)
    val totalDept: BigDecimal,

    val currentCreditNumber: Int,
    val requestNumber: Long,
)
