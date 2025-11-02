package com.example.myapplication.creditcards.data.mock.models

import kotlinx.serialization.Serializable

@Serializable
data class CreditCardTermsDto(
    val cashback: Double,
    val maxCount: Int,
    val serviceCost: Long,
    val maxCreditLimit: Long,
)