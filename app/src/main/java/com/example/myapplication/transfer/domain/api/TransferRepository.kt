package com.example.myapplication.transfer.domain.api

import com.example.myapplication.core.data.network.Response

interface TransferRepository {
    @Suppress("LongParameterList")
    suspend fun transfer(
        fromId: Long,
        fromType: String,
        toId: Long,
        toType: String,
        transactionNumber: Long,
        userId: Long
    ): Response
}