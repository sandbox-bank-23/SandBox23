package com.example.myapplication.debitcards.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.core.domain.models.Card
import com.example.myapplication.core.domain.models.Result
import com.example.myapplication.debitcards.domain.api.CheckDebitCardCountUseCase
import com.example.myapplication.debitcards.domain.api.CreateDebitCardUseCase
import com.example.myapplication.debitcards.domain.api.GetDebitCardTermsUseCase
import com.example.myapplication.debitcards.ui.state.DebitCardsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DebitCardsViewModel(
    private val createDebitCardUseCase: CreateDebitCardUseCase,
    private val checkDebitCardCountUseCase: CheckDebitCardCountUseCase,
    private val getDebitCardTermsUseCase: GetDebitCardTermsUseCase
) : ViewModel() {

    private val _debitCardsState = MutableStateFlow<DebitCardsState>(
        value = DebitCardsState.Loading
    )
    val debitCardsState: StateFlow<DebitCardsState> = _debitCardsState.asStateFlow()
    var debitCardMaxCount: Int = 0

    fun createCard(userId: Long) {
        viewModelScope.launch {
            createDebitCardUseCase.createDebitCard(userId).collect { result ->
                processResult(result)
            }
        }
    }

    fun getDebitCardTerms(userId: Long) {
        viewModelScope.launch {
            getDebitCardTermsUseCase.getDebitCardTerms().collect { result ->
                debitCardMaxCount = (result as Result.Success).data.maxCount
                renderState(
                    DebitCardsState.Content(result.data)
                )
            }
            if (checkDebitCardCountUseCase.isCardCountLimit(userId, debitCardMaxCount)) {
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