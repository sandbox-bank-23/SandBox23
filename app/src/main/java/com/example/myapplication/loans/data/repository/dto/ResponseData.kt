package com.example.myapplication.loans.data.repository.dto

import com.example.myapplication.loans.domain.model.Credit
import kotlinx.serialization.Serializable

@Serializable
data class ResponseData(
    val body: Credit? = null,

    val requestNumber: Long,
    val currentCreditNumber: Int,
)
