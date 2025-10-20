package com.example.myapplication.core.domain.models.loan

import java.math.BigDecimal

//monthPay - месячный платеж
//balance - сумма кредита
//orderDate - дата оформления заявки
//period - срок кредита
//interestRate - процентная ставка

data class Credit(
    val userId: Long,
    val monthPay: BigDecimal,
    val balance: BigDecimal,
    val orderDate: Long,
    val period: Long,
    val interestRate: Long,
) {
    val name = listOf(
        "Автокредит",
        "Ипотека",
        "Кредит наличными"
    ).random()
}