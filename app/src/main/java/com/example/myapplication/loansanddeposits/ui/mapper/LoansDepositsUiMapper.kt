@file:Suppress("MagicNumber")

package com.example.myapplication.loansanddeposits.ui.mapper

import com.example.myapplication.core.domain.models.Product
import com.example.myapplication.core.domain.models.ProductType
import com.example.myapplication.loansanddeposits.ui.state.CreditType
import com.example.myapplication.loansanddeposits.ui.state.CreditUi
import com.example.myapplication.loansanddeposits.ui.state.DepositUi
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.max
import kotlin.math.min

interface LoansDepositsUiMapper {
    fun toDepositUi(p: Product): DepositUi
    fun toCreditUi(p: Product): CreditUi

    fun split(products: List<Product>): Pair<List<DepositUi>, List<CreditUi>> =
        products.fold(mutableListOf<DepositUi>() to mutableListOf<CreditUi>()) { acc, p ->
            when (p.type) {
                ProductType.DEPOSIT -> acc.first += toDepositUi(p)
                ProductType.LOAN -> acc.second += toCreditUi(p)
            }
            acc
        }.let { it.first to it.second }
}

class DefaultLoansDepositsUiMapper : LoansDepositsUiMapper {

    private val rubFormatter = NumberFormat.getCurrencyInstance(Locale("ru", "RU")).apply {
        minimumFractionDigits = 2;
        maximumFractionDigits = 2
    }
    private val dayMonthFormatter = DateTimeFormatter.ofPattern("d MMMM", Locale("ru"))

    override fun toDepositUi(p: Product) = DepositUi(
        id = p.id,
        title = "Вклад №${p.id}",
        balanceText = p.balance.toRub(),
        percentType = p.percent.toInt()
    )

    override fun toCreditUi(p: Product): CreditUi {
        val months = max(1, p.period.toInt())
        val monthly = p.balance / months
        val nextDate = nextChargeDate(p).format(dayMonthFormatter)
        return CreditUi(
            id = p.id,
            name = if (p.percentType.toInt() == 1) "Автокредит" else "Ипотека",
            amountText = "$nextDate спишем ${monthly.toRub()}",
            type = if (p.percentType.toInt() == 1) CreditType.Auto else CreditType.Mortgage
        )
    }

    private fun Long.toRub(): String = rubFormatter.format(this)

    private fun nextChargeDate(p: Product): LocalDate {
        val today = LocalDate.now()
        val preferredDay = (p.id % 28).toInt().coerceAtLeast(1)
        val day = min(preferredDay, today.lengthOfMonth())
        val thisMonth = today.withDayOfMonth(day)
        return if (thisMonth.isBefore(today)) thisMonth.plusMonths(1) else thisMonth
    }
}