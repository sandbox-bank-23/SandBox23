package com.example.myapplication.deposits.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.core.domain.api.AppInteractor
import com.example.myapplication.core.domain.api.StorageKey
import com.example.myapplication.deposits.domain.DepositResult
import com.example.myapplication.deposits.domain.usecase.inter.OpenDepositUseCase
import com.example.myapplication.deposits.ui.state.NewDepositScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewDepositViewModel(
    private val openDepositUseCase: OpenDepositUseCase,
    private val appInteractor: AppInteractor
) : ViewModel() {

    private val _newDepositScreenState =
        MutableStateFlow<NewDepositScreenState>(NewDepositScreenState.Loading)
    val newDepositScreenState = _newDepositScreenState.asStateFlow()

    @Suppress("MagicNumber")
    private val _initialCharacteristics = MutableStateFlow(listOf(3, 6, 9, 12, 24))
    val initialCharacteristics = _initialCharacteristics.asStateFlow()

    fun openDep(period: Long) {
        viewModelScope.launch {
            appInteractor.getAuthDataValue(StorageKey.AUTHDATA).collect { authData ->
                authData?.userId?.let { userId ->
                    val result = openDepositUseCase(
                        currentDepositNumber = 0L,
                        requestNumber = 0L,
                        userId = userId.toLong(),
                        percentType = 9L,
                        period = period
                    )
                    when (result) {
                        is DepositResult.Success -> {
                            _newDepositScreenState.value =
                                NewDepositScreenState.Content(result.data)
                        }

                        is DepositResult.DataBaseError -> {
                            _newDepositScreenState.value =
                                NewDepositScreenState.Error(result.message)
                        }

                        is DepositResult.Empty -> {
                            _newDepositScreenState.value = NewDepositScreenState.Empty
                        }

                        is DepositResult.InputError -> {
                            _newDepositScreenState.value =
                                NewDepositScreenState.Error(result.message)
                        }

                        is DepositResult.NetworkError -> {
                            _newDepositScreenState.value =
                                NewDepositScreenState.Error(result.message)
                        }
                    }
                }
            }
        }
    }
}