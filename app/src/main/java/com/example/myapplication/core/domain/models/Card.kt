package com.example.myapplication.core.domain.models

import androidx.annotation.StringDef
import com.example.myapplication.core.domain.models.CardType.Companion.CREDIT
import com.example.myapplication.core.domain.models.CardType.Companion.DEBIT
import kotlinx.serialization.Serializable

@Serializable
data class Card(
    val id: Long,
    val cvv: Long,
    val endDate: String,
    val owner: String,
    val userId: Long,
    @CardType val type: String,
    val percent: Double,
    // @Serializable(with = BigDecimalAsStringSerializer::class)
    val balance: Long
)


// Это нужно для того, чтобы гарантировать,
// что поле type может хранить только "Credit" или только "Debit"
@StringDef(CREDIT, DEBIT)
@Retention(AnnotationRetention.SOURCE)
annotation class CardType {
    companion object {
        const val CREDIT = "Credit"
        const val DEBIT = "Debit"
    }
}
