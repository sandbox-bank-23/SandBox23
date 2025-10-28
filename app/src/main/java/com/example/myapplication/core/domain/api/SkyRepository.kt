@file:Suppress("Indentation")

package com.example.myapplication.core.domain.api

import com.example.myapplication.core.domain.models.ProductType
import com.example.myapplication.core.domain.models.Result

interface SkyRepository {
    suspend fun replenishment(
        toId: Long,
        @ProductType toType: String,
        value: Long,
        transactionNumber: Long
        ): Result<Long>
}