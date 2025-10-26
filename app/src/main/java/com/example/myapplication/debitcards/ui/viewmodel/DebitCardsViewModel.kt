package com.example.myapplication.debitcards.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.core.domain.models.Card
import com.example.myapplication.core.domain.models.Result
import com.example.myapplication.debitcards.domain.api.CheckDebitCardCountUseCase
import com.example.myapplication.debitcards.domain.api.CreateDebitCardUseCase
import com.example.myapplication.debitcards.ui.state.DebitCardsState
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
            createDebitCardUseCase.createDebitCard(userId).collect {
                result -> processResult(result)
            }
        }
    }

    fun checkCardCount(userId: Long) {
        viewModelScope.launch {
            if (checkDebitCardCountUseCase.isCardCountLimit(userId)) {
                renderState(DebitCardsState.Limit)
            }
        }
    }

    private fun processResult(result: Result<Card>) {
        when (result) {
            is Result.Error -> renderState(DebitCardsState.Error)
            is Result.Success -> renderState(DebitCardsState.Success)
        }
    }

    private fun renderState(state: DebitCardsState) {
        _debitCardsState.value = state
    }

}