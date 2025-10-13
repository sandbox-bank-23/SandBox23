@file:Suppress("Indentation")

package com.example.myapplication.core.domain.api

import com.example.myapplication.core.domain.models.ProductType
import com.example.myapplication.core.domain.models.Response

interface SkyRepository {
    suspend fun replenishment(
        toId: Long,
        @ProductType toType: String,
        value: Long,
        transactionNumber: Long
        ): Response
}