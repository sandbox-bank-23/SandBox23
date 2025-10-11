package com.example.myapplication.core.domain.models

import androidx.annotation.StringDef
import com.example.myapplication.core.domain.models.ProductType.Companion.DEPOSIT
import com.example.myapplication.core.domain.models.ProductType.Companion.LOAN

data class Product(
    val id: Long,
    @ProductType val type: String,
    val percentType: Long,
    val period: Long,
    val percent: Long,
    val balance: Long
)

// Это нужно для того, чтобы гарантировать,
// что в поле type может хранить только "Loan" или только "Deposit"
@StringDef(LOAN, DEPOSIT)
@Retention(AnnotationRetention.SOURCE)
annotation class ProductType {
    companion object {
        const val LOAN = "Loan"
        const val DEPOSIT = "Deposit"
    }
}