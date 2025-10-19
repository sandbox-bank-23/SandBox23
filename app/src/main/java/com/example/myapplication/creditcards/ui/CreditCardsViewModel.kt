package com.example.myapplication.creditcards.ui

import androidx.lifecycle.ViewModel
import com.example.myapplication.creditcards.domain.models.CreditCardsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CreditCardsViewModel : ViewModel() {

    private val _creditCardsState = MutableStateFlow<CreditCardsState>(
        value = CreditCardsState.Content
    )
    val creditCardsState: StateFlow<CreditCardsState> = _creditCardsState.asStateFlow()

    fun openCard() {
        renderState(CreditCardsState.Offline)
    }

    private fun renderState(state: CreditCardsState) {
        _creditCardsState.value = state
    }
}