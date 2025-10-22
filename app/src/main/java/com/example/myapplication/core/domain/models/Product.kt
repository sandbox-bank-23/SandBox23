@file:Suppress("ImportOrdering")

package com.example.myapplication.core.domain.models

import androidx.annotation.StringDef
import com.example.myapplication.core.domain.models.ProductType.Companion.DEPOSIT
import com.example.myapplication.core.domain.models.ProductType.Companion.LOAN
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Product(
    val id: Long,
    @ProductType val type: String,
    @SerialName("percent_type") val percentType: Long,
    val period: Long,
    val percent: Long,
    val balance: Long
)

@StringDef(LOAN, DEPOSIT)
@Retention(AnnotationRetention.SOURCE)
annotation class ProductType {
    companion object {
        const val LOAN = "Loan"
        const val DEPOSIT = "Deposit"
    }
}
