package com.example.myapplication.debitcards.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.debitcards.domain.api.CheckDebitCardCountUseCase
import com.example.myapplication.debitcards.domain.api.CreateDebitCardUseCase
import com.example.myapplication.debitcards.domain.models.DebitCardsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DebitCardsViewModel(
    private val createDebitCardUseCase: CreateDebitCardUseCase,
    private val checkDebitCardCountUseCase: CheckDebitCardCountUseCase
) : ViewModel() {

    private val _debitCardsState = MutableStateFlow<DebitCardsState>(
        value = DebitCardsState.Content
    )
    val debitCardsState: StateFlow<DebitCardsState> = _debitCardsState.asStateFlow()

    fun createCard(userId: Long) {
        viewModelScope.launch {
            // val response = createDebitCardUseCase.createDebitCard(userId)
        }
        renderState(DebitCardsState.Success)
    }

    fun checkCardCount(userId: Long) {
        viewModelScope.launch {
            if (checkDebitCardCountUseCase.isCardCountLimit(userId)) {
                renderState(DebitCardsState.Error)
            }
        }
    }

    private fun renderState(state: DebitCardsState) {
        _debitCardsState.value = state
    }

}