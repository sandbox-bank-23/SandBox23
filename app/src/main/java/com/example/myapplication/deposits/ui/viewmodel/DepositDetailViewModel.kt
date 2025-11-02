package com.example.myapplication.deposits.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.core.domain.api.AppInteractor
import com.example.myapplication.core.domain.api.StorageKey
import com.example.myapplication.deposits.domain.DepositResult
import com.example.myapplication.deposits.domain.usecase.inter.CloseDepositUseCase
import com.example.myapplication.deposits.domain.usecase.inter.TakeDepositUseCase
import com.example.myapplication.deposits.ui.state.DepositDeletedState
import com.example.myapplication.deposits.ui.state.DepositDetailScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DepositDetailViewModel(
    private val takeDepositUseCase: TakeDepositUseCase,
    private val closeDepositUseCase: CloseDepositUseCase,
    private val appInteractor: AppInteractor
) : ViewModel() {

    private val _depositDetailScreenState =
        MutableStateFlow<DepositDetailScreenState>(DepositDetailScreenState.Empty)
    val depositDetailScreenState = _depositDetailScreenState.asStateFlow()

    private val _depositExistState = MutableStateFlow(DepositDeletedState.EXIST)
    val depositExistState = _depositExistState.asStateFlow()

    fun loadDeposit(id: Long) {
        viewModelScope.launch {
            val result = takeDepositUseCase(id)
            when (result) {
                is DepositResult.DataBaseError -> {
                    _depositDetailScreenState.value = DepositDetailScreenState.Error(result.message)
                }

                is DepositResult.Empty -> {
                    _depositDetailScreenState.value = DepositDetailScreenState.Error(result.message)
                }

                is DepositResult.InputError -> {
                    _depositDetailScreenState.value = DepositDetailScreenState.Error(result.message)
                }

                is DepositResult.NetworkError -> {
                    _depositDetailScreenState.value = DepositDetailScreenState.Error(result.message)
                }

                is DepositResult.Success -> {
                    _depositDetailScreenState.value = DepositDetailScreenState.Content(result.data)
                }
            }
        }
    }

    fun closeDeposit(id: Long) {
        viewModelScope.launch {
            appInteractor.getAuthDataValue(StorageKey.AUTHDATA).collect { authData ->
                authData?.userId?.let { userId ->
                    val result = closeDepositUseCase(id, 0L, userId.toLong())
                    when (result) {
                        is DepositResult.DataBaseError,
                        is DepositResult.InputError,
                        is DepositResult.NetworkError,
                        is DepositResult.Empty -> {
                            _depositExistState.value = DepositDeletedState.EXIST
                        }

                        is DepositResult.Success -> {
                            _depositExistState.value = DepositDeletedState.DELETED
                        }
                    }
                }
            }
        }
    }
}