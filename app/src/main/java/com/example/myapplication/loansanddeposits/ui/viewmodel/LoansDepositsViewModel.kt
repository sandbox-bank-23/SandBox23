@file:Suppress("MagicNumber")

package com.example.myapplication.loansanddeposits.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.core.domain.api.AppInteractor
import com.example.myapplication.core.domain.api.StorageKey
import com.example.myapplication.core.domain.models.Result
import com.example.myapplication.core.utils.ApiCodes
import com.example.myapplication.loansanddeposits.domain.LoansAndDepositsUseCase
import com.example.myapplication.loansanddeposits.ui.mapper.LoansDepositsUiMapper
import com.example.myapplication.loansanddeposits.ui.state.LoansDepositsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoansDepositsViewModel(
    private val usecase: LoansAndDepositsUseCase,
    private val mapper: LoansDepositsUiMapper,
    private val appInteractor: AppInteractor
) : ViewModel() {

    private val _state = MutableStateFlow<LoansDepositsState>(LoansDepositsState.Loading)
    val state: StateFlow<LoansDepositsState> = _state.asStateFlow()

    fun requestData() = viewModelScope.launch {
        _state.value = LoansDepositsState.Loading
        appInteractor.getAuthDataValue(StorageKey.AUTHDATA).collect { authData ->
            authData?.userId?.let { userId ->
                val res = usecase.getAllLoansAndDeposits(userId.toLong())
                res.collect { res ->
                    when (res) {
                        is Result.Success -> {
                            val (deposits, credits) = mapper.split(res.data)
                            _state.value = LoansDepositsState.Content(deposits, credits)
                        }

                        is Result.Error -> {
                            if (res.message.contains(ApiCodes.NO_RESPONSE.toString(), true)) {
                                _state.value = LoansDepositsState.Offline
                            } else {
                                _state.value = LoansDepositsState.Error(res.message)
                            }
                        }
                    }

                }
            }
        }
    }

    fun retry() = requestData()
}