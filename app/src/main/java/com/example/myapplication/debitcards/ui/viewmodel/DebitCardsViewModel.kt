package com.example.myapplication.debitcards.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.core.domain.api.AppInteractor
import com.example.myapplication.core.domain.api.StorageKey
import com.example.myapplication.core.domain.models.Card
import com.example.myapplication.core.domain.models.Result
import com.example.myapplication.debitcards.domain.api.CheckDebitCardCountUseCase
import com.example.myapplication.debitcards.domain.api.CreateDebitCardUseCase
import com.example.myapplication.debitcards.domain.api.GetDebitCardTermsUseCase
import com.example.myapplication.debitcards.domain.models.DebitCardResult
import com.example.myapplication.debitcards.ui.state.DebitCardsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DebitCardsViewModel(
    private val createDebitCardUseCase: CreateDebitCardUseCase,
    private val checkDebitCardCountUseCase: CheckDebitCardCountUseCase,
    private val getDebitCardTermsUseCase: GetDebitCardTermsUseCase,
    private val appInteractor: AppInteractor,
) : ViewModel() {

    private val _debitCardsState = MutableStateFlow<DebitCardsState>(
        value = DebitCardsState.Loading
    )
    val debitCardsState: StateFlow<DebitCardsState> = _debitCardsState.asStateFlow()
    var debitCardMaxCount: Int = 0

    fun createCard() {
        viewModelScope.launch {
            appInteractor.getAuthDataValue(StorageKey.AUTHDATA).collect { data ->
                data?.let { authData ->
                    authData.userId?.let { userId ->
                        createDebitCardUseCase.createDebitCard(userId.toLong()).collect { result ->
                            processResult(result)
                        }
                    }
                }
            }
        }
    }

    fun getDebitCardTerms() {
        viewModelScope.launch {
            appInteractor.getAuthDataValue(StorageKey.AUTHDATA).collect { data ->
                data?.let { authData ->
                    authData.userId?.let { userId ->
                        getDebitCardTermsUseCase.getDebitCardTerms().collect { result ->
                            debitCardMaxCount = (result as Result.Success).data.maxCount
                            renderState(
                                DebitCardsState.Content(result.data)
                            )
                        }
                        if (checkDebitCardCountUseCase.isCardCountLimit(
                                userId.toLongOrNull() ?: -1L,
                                debitCardMaxCount
                            )
                        ) {
                            renderState(DebitCardsState.Limit)
                        }
                    }
                }
            }
        }
    }

    private fun processResult(result: DebitCardResult<Card>) {
        Log.d("VM STATE", result.toString())
        when (result) {
            is DebitCardResult.Error -> renderState(DebitCardsState.Error)
            is DebitCardResult.Success -> renderState(DebitCardsState.Success)
            is DebitCardResult.LimitError -> renderState(DebitCardsState.Limit)
            is DebitCardResult.NetworkError -> renderState(DebitCardsState.Online)
        }
    }

    private fun renderState(state: DebitCardsState) {
        _debitCardsState.value = state
    }

}