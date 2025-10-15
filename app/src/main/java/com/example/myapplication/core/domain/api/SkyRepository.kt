@file:Suppress("Indentation")

package com.example.myapplication.core.domain.api

import com.example.myapplication.core.data.network.Response
import com.example.myapplication.core.domain.models.ProductType

interface SkyRepository {
    suspend fun replenishment(
        toId: Long,
        @ProductType toType: String,
        value: Long,
        transactionNumber: Long
        ): Response
}