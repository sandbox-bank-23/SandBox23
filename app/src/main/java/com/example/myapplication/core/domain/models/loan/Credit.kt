package com.example.myapplication.core.domain.models.loan

import java.math.BigDecimal

private const val CREDIT_NAME = "Потребительский кредит"

// monthPay - месячный платеж
// balance - сумма кредита
// orderDate - дата оформления заявки
// endDate - дата завершения кредита
// period - срок кредита
// percent - процентная ставка

data class Credit(
    val id: Long,
    val userId: Long,
    val name: String = CREDIT_NAME,
    val balance: BigDecimal,
    val period: Long,

    val orderDate: Long? = null,
    val endDate: Long? = null,
    val percent: Long? = null,
    val isClose: Boolean? = null,
    val monthPay: BigDecimal? = null,
)