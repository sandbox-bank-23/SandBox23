package com.example.myapplication.debitcards.ui

import androidx.lifecycle.ViewModel
import com.example.myapplication.debitcards.domain.models.DebitCardsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DebitCardsViewModel : ViewModel() {

    private val _debitCardsState = MutableStateFlow<DebitCardsState>(
        value = DebitCardsState.Content
    )
    val debitCardsState: StateFlow<DebitCardsState> = _debitCardsState.asStateFlow()

    fun openCard() {
        renderState(DebitCardsState.Offline)
    }

    private fun renderState(state: DebitCardsState) {
        _debitCardsState.value = state
    }

}