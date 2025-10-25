package com.example.myapplication.creditcards.data.mock.models

import kotlinx.serialization.Serializable

@Serializable
data class ResponseData(
    val card: Card?,
    val requestNumber: Long,
    val currentCreditNumber: Long,
)