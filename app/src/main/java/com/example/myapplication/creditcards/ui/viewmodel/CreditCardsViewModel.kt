package com.example.myapplication.creditcards.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.core.domain.models.Card
import com.example.myapplication.core.domain.models.Result
import com.example.myapplication.creditcards.domain.api.CheckCreditCardCountUseCase
import com.example.myapplication.creditcards.domain.api.CreateCreditCardUseCase
import com.example.myapplication.creditcards.ui.state.CreditCardsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreditCardsViewModel(
    private val createCreditCardUseCase: CreateCreditCardUseCase,
    private val checkCreditCardCountUseCase: CheckCreditCardCountUseCase
) : ViewModel() {

    private val _creditCardsState = MutableStateFlow<CreditCardsState>(
        value = CreditCardsState.Content
    )
    val creditCardsState: StateFlow<CreditCardsState> = _creditCardsState.asStateFlow()
    var creditLimitValue = 0L


    fun createCard(userId: Long) {
        viewModelScope.launch {
            createCreditCardUseCase.createCreditCard(
                userId,
                creditLimitValue.toBigDecimal()
            ).collect {
                result -> processResult(result)
            }
        }
    }

    fun checkCardCount(userId: Long) {
        viewModelScope.launch {
            if (checkCreditCardCountUseCase.isCardCountLimit(userId)) {
                renderState(CreditCardsState.Limit)
            }
        }
    }

    private fun processResult(result: Result<Card>) {
        when (result) {
            is Result.Error -> renderState(CreditCardsState.Error)
            is Result.Success -> renderState(CreditCardsState.Success)
        }
    }

    private fun renderState(state: CreditCardsState) {
        _creditCardsState.value = state
    }
}