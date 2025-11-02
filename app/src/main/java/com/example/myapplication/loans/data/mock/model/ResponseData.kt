package com.example.myapplication.loans.data.mock.model

import kotlinx.serialization.Serializable

@Serializable
data class ResponseData(
    val body: Credit?,

    val requestNumber: Long,
    val currentCreditNumber: Int,
)
