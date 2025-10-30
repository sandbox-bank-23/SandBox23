package com.example.myapplication.creditcards.data.repo.dto

import com.example.myapplication.core.domain.models.Card
import kotlinx.serialization.Serializable

@Serializable
data class ResponseData(
    val card: Card?,
    val requestNumber: Long,
    val currentCreditNumber: Long,
)
