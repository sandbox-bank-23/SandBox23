package com.example.myapplication.loans.domain.model

import com.example.myapplication.utils.BigDecimalAsStringSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal

const val CREDIT_NAME = "Потребительский кредит"

// monthPay - месячный платеж
// balance - сумма кредита
// orderDate - дата оформления заявки
// endDate - дата завершения кредита
// period - срок кредита
// percent - процентная ставка

@Serializable
data class Credit(
    val id: Long? = null,
    val userId: Long,
    val name: String,

    @Serializable(with = BigDecimalAsStringSerializer::class)
    val balance: BigDecimal,
    val period: Long,
    val orderDate: Long,

    val endDate: Long? = null,
    val percent: Long? = null,
    val isClose: Boolean? = null,

    @Serializable(with = BigDecimalAsStringSerializer::class)
    val monthPay: BigDecimal? = null,
)