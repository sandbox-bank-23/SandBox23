package com.example.myapplication.creditcards.ui

import androidx.lifecycle.ViewModel
import com.example.myapplication.creditcards.domain.models.CreditCardsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CreditCardsViewModel : ViewModel() {

    private val _creditCardsState = MutableStateFlow<CreditCardsState>(
        value = CreditCardsState.Error
    )
    val creditCardsState: StateFlow<CreditCardsState> = _creditCardsState.asStateFlow()
    var creditLimitValue = 0L


    fun openCard() {
        renderState(CreditCardsState.Success)
    }

    private fun renderState(state: CreditCardsState) {
        _creditCardsState.value = state
    }
}