package com.example.myapplication.deposits.ui.utils

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun BigDecimal.toMoneyString(): String {
    val formatter = DecimalFormat("#,###.00")
    formatter.decimalFormatSymbols = DecimalFormatSymbols(Locale("ru")).apply {
        groupingSeparator = ' '
        decimalSeparator = ','
    }
    return formatter.format(this)
}