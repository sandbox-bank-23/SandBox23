package com.example.myapplication.creditcards.data.repo.dto

import com.example.myapplication.utils.BigDecimalAsStringSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class RequestData(
    val currentCardNumber: Long,
    val requestNumber: Long,
    val cardType: String,
    val userId: Long,
    val owner: String,
    @Serializable(with = BigDecimalAsStringSerializer::class)
    val balance: BigDecimal
)

