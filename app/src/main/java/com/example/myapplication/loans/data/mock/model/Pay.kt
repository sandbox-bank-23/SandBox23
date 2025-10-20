package com.example.myapplication.loans.data.mock.model

import com.example.myapplication.utils.BigDecimalAsStringSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class Pay(
    @Serializable(with = BigDecimalAsStringSerializer::class)
    val sum: BigDecimal
)
