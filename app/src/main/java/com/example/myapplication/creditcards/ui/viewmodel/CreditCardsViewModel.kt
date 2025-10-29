package com.example.myapplication.creditcards.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.core.domain.api.AppInteractor
import com.example.myapplication.core.domain.api.StorageKey
import com.example.myapplication.core.domain.models.Card
import com.example.myapplication.core.domain.models.Result
import com.example.myapplication.creditcards.domain.api.CheckCreditCardCountUseCase
import com.example.myapplication.creditcards.domain.api.CreateCreditCardUseCase
import com.example.myapplication.creditcards.domain.api.GetCreditCardTermsUseCase
import com.example.myapplication.creditcards.domain.models.CreditCardResult
import com.example.myapplication.creditcards.ui.state.CreditCardsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreditCardsViewModel(
    private val createCreditCardUseCase: CreateCreditCardUseCase,
    private val checkCreditCardCountUseCase: CheckCreditCardCountUseCase,
    private val getCreditCardTermsUseCase: GetCreditCardTermsUseCase,
    private val appInteractor: AppInteractor,
) : ViewModel() {

    private val _creditCardsState = MutableStateFlow<CreditCardsState>(
        value = CreditCardsState.Loading
    )
    val creditCardsState: StateFlow<CreditCardsState> = _creditCardsState.asStateFlow()
    var creditLimitValue = 0L
    var creditCardMaxCount: Int = 0


    fun createCard(userId: Long) {
        viewModelScope.launch {
            appInteractor.getAuthDataValue(StorageKey.AUTHDATA).collect { data ->
                data?.let { authData ->
                    authData.userId?.let { userId ->
                        createCreditCardUseCase.createCreditCard(
                            userId.toLong(),
                            creditLimitValue.toBigDecimal()
                        ).collect { result ->
                            processResult(result)
                        }
                    }
                }
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

    private fun processResult(result: CreditCardResult<Card>) {
        when (result) {
            is CreditCardResult.Error -> renderState(CreditCardsState.Error)
            is CreditCardResult.LimitError -> renderState(CreditCardsState.Limit)
            is CreditCardResult.NetworkError -> renderState(CreditCardsState.Online)
            is CreditCardResult.Success -> renderState(CreditCardsState.Success)
        }
    }

    private fun renderState(state: CreditCardsState) {
        _creditCardsState.value = state
    }
}