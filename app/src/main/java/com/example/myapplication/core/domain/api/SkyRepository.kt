@file:Suppress("Indentation")

package com.example.myapplication.core.domain.api

import com.example.myapplication.auth.domain.state.Result
import com.example.myapplication.core.domain.models.ProductType

interface SkyRepository {
    suspend fun replenishment(
        toId: Long,
        @ProductType toType: String,
        value: Long,
        transactionNumber: Long
        ): Result<Long>
}