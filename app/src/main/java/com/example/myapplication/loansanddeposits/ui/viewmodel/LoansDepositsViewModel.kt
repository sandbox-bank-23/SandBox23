@file:Suppress("MagicNumber")

package com.example.myapplication.loansanddeposits.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.core.domain.models.ProductType
import com.example.myapplication.core.domain.models.Result
import com.example.myapplication.core.utils.ApiCodes
import com.example.myapplication.loansanddeposits.domain.api.LoansAndDepositsRepository
import com.example.myapplication.loansanddeposits.ui.state.CreditType
import com.example.myapplication.loansanddeposits.ui.state.CreditUi
import com.example.myapplication.loansanddeposits.ui.state.DepositUi
import com.example.myapplication.loansanddeposits.ui.state.LoansDepositsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.max

class LoansDepositsViewModel(
    private val repository: LoansAndDepositsRepository
) : ViewModel() {

    private val _state = MutableStateFlow<LoansDepositsState>(LoansDepositsState.Loading)
    val state: StateFlow<LoansDepositsState> = _state.asStateFlow()

    fun requestData() = viewModelScope.launch {
        _state.value = LoansDepositsState.Loading
        when (val res = repository.getAllLoansAndDeposits()) {
            is Result.Success -> {
                val products = res.data
                val deposits = products.filter { it.type == ProductType.DEPOSIT }.map {
                    DepositUi(
                        id = it.id,
                        title = "Вклад №${it.id}",
                        balanceText = it.balance.toRub(),
                        percentType = it.percent.toInt()
                    )
                }
                val credits = products.filter { it.type == ProductType.LOAN }.map {
                    val months = max(1, it.period.toInt())
                    val monthly = it.balance / months
                    CreditUi(
                        id = it.id,
                        name = "Кредит №${it.id}",
                        amountText = "Платёж: ${monthly.toRub()}",
                        type = if (it.percentType.toInt() == 1) CreditType.Auto else CreditType.Mortgage
                    )
                }
                _state.value = LoansDepositsState.Content(deposits, credits)
            }

            is Result.Error -> {
                if (res.message.contains(ApiCodes.NO_RESPONSE.toString(), true)) {
                    _state.value = LoansDepositsState.Offline
                } else {
                    _state.value = LoansDepositsState.Error(res.message)
                }
            }
        }
    }

    fun retry() = requestData()
}

private fun Long.toRub(): String =
    NumberFormat.getCurrencyInstance(Locale("ru", "RU")).format(this)
