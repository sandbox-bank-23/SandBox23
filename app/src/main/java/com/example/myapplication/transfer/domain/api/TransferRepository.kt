package com.example.myapplication.transfer.domain.api

import com.example.myapplication.core.domain.models.Response

interface TransferRepository {
    suspend fun transfer(
        fromId: Long,
        fromType: String,
        toId: Long,
        toType: String,
        transactionNumber: Long,
        userId: Long
    ): Response
}