package com.example.myapplication.debitcards.data.repo.dto

import kotlinx.serialization.Serializable

@Serializable
data class RequestData(
    val currentCardNumber: Long,
    val requestNumber: Long,
    val cardType: String,
    val userId: Long,
    val owner: String,
)
