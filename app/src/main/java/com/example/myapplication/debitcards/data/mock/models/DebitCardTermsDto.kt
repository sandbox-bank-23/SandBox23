package com.example.myapplication.debitcards.data.mock.models

import kotlinx.serialization.Serializable

@Serializable
data class DebitCardTermsDto(
    val cashback: Double,
    val maxCount: Int,
    val serviceCost: Long
)