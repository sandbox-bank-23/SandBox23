@file:Suppress("Indentation")

package com.example.myapplication.core.domain.api

import com.example.myapplication.core.domain.models.ProductType
import com.example.myapplication.core.data.network.Response

interface SkyRepository {
    suspend fun replenishment(
        toId: Long,
        @ProductType toType: String,
        value: Long,
        transactionNumber: Long
        ): Response
}