package com.example.myapplication.loansanddeposits.data.dto

import com.example.myapplication.core.data.network.Response
import kotlinx.serialization.Serializable

@Serializable
data class LoansDepositsResponse(
    val data: Response
)