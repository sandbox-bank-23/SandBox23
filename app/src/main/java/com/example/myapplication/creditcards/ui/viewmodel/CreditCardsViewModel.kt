package com.example.myapplication.creditcards.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.core.domain.models.Card
import com.example.myapplication.core.domain.models.Result
import com.example.myapplication.creditcards.domain.api.CheckCreditCardCountUseCase
import com.example.myapplication.creditcards.domain.api.CreateCreditCardUseCase
import com.example.myapplication.creditcards.domain.api.GetCreditCardTermsUseCase
import com.example.myapplication.creditcards.ui.state.CreditCardsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreditCardsViewModel(
    private val createCreditCardUseCase: CreateCreditCardUseCase,
    private val checkCreditCardCountUseCase: CheckCreditCardCountUseCase,
    private val getCreditCardTermsUseCase: GetCreditCardTermsUseCase
) : ViewModel() {

    private val _creditCardsState = MutableStateFlow<CreditCardsState>(
        value = CreditCardsState.Loading
    )
    val creditCardsState: StateFlow<CreditCardsState> = _creditCardsState.asStateFlow()
    var creditLimitValue = 0L
    var creditCardMaxCount: Int = 0


    fun createCard(userId: Long) {
        viewModelScope.launch {
            createCreditCardUseCase.createCreditCard(
                userId,
                creditLimitValue.toBigDecimal()
            ).collect { result ->
                processResult(result)
            }
        }
    }


    fun getCreditCardTerms(userId: Long) {
        viewModelScope.launch {
            getCreditCardTermsUseCase.getCreditCardTerms().collect {
                    result ->
                creditCardMaxCount = (result as Result.Success).data.maxCount
                renderState(
                    CreditCardsState.Content(result.data)
                )
            }
            if (checkCreditCardCountUseCase.isCardCountLimit(userId, creditCardMaxCount)) {
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