package com.example.myapplication.creditcards.data.mock.models

import com.example.myapplication.utils.BigDecimalAsStringSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class Card(
    val id: Long,
    val cvv: Long,
    val endDate: String,
    val owner: String,
    val type: String,
    val percent: Double,
    val userId: Long,
    @Serializable(with = BigDecimalAsStringSerializer::class)
    val balance: BigDecimal
)
