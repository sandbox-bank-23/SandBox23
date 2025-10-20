@file:Suppress("MagicNumber")

package com.example.myapplication.loansanddeposits.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.loansanddeposits.ui.state.CreditType
import com.example.myapplication.loansanddeposits.ui.state.CreditUi
import com.example.myapplication.loansanddeposits.ui.state.DepositUi
import com.example.myapplication.loansanddeposits.ui.state.LoansDepositsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoansDepositsViewModel : ViewModel() {

    private val _state = MutableStateFlow<LoansDepositsState>(LoansDepositsState.Loading)
    val state: StateFlow<LoansDepositsState> = _state.asStateFlow()

    fun requestData() {
        val deposits = listOf(
            DepositUi(1L, "Накопительный вклад", "10 000,00 ₽", 8),
            DepositUi(2L, "До востребования", "30 000,00 ₽", 4),
        )
        val credits = listOf(
            CreditUi(3L, "Автокредит", "15 июня спишем 23 700 ₽", CreditType.Auto),
            CreditUi(4L, "Ипотека", "7 июня спишем 128 700 ₽", CreditType.Mortgage),
        )
        _state.value = LoansDepositsState.Content(deposits = deposits, credits = credits)
    }

    fun goOffline() {
        _state.value = LoansDepositsState.Offline
    }

    fun showError(message: String) {
        _state.value = LoansDepositsState.Error(message)
    }
}
