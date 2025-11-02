package com.example.myapplication.auth.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.auth.domain.AuthInteractor
import com.example.myapplication.auth.domain.model.AuthData
import com.example.myapplication.auth.domain.model.AuthRequest
import com.example.myapplication.auth.ui.state.RegScreenState
import com.example.myapplication.auth.ui.utils.ValidationUtils
import com.example.myapplication.core.domain.api.AppInteractor
import com.example.myapplication.core.domain.api.StorageKey
import com.example.myapplication.core.domain.models.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val appInteractor: AppInteractor,
    private val authInteractor: AuthInteractor
) : ViewModel() {

    private val _screenState = MutableStateFlow<RegScreenState>(
        RegScreenState.Default
    )
    val screenState: StateFlow<RegScreenState> = _screenState.asStateFlow()

    // StateFlow для логина
    private val _loginState = MutableStateFlow("")
    val loginState: StateFlow<String> = _loginState.asStateFlow()

    // StateFlow для пароля
    private val _passwordState = MutableStateFlow<String>("")
    val passwordState: StateFlow<String> = _passwordState.asStateFlow()

    // StateFlow для еще раз пароля
    private val _password2State = MutableStateFlow<String>("")
    val password2State: StateFlow<String> = _password2State.asStateFlow()

    fun register() {
        val emailCheck = ValidationUtils.isEmailValid(loginState.value)
        val passwordCheck = ValidationUtils.isPasswordValid(passwordState.value)

        val isEmailError = emailCheck.first || emailCheck.second
        val isPasswordError = passwordCheck.first || passwordCheck.second

        if (isEmailError || isPasswordError || passwordState.value != password2State.value) {
            _screenState.value = RegScreenState.ErrorState(
                emailLengthError = emailCheck.first,
                emailConsistError = emailCheck.second,
                passEmptyError = passwordCheck.first,
                passLengthError = passwordCheck.second,
                passDiffError = passwordState.value != password2State.value
            )
        } else {
            viewModelScope.launch {
                processResult(
                    authInteractor.register(
                        AuthRequest(
                            loginState.value,
                            passwordState.value
                        )
                    )
                )
            }
        }
    }

    fun onLoginChange(login: String) {
        _loginState.value = login
    }

    fun onPasswordChange(password: String) {
        _passwordState.value = password
    }

    fun onPassword2Change(password: String) {
        _password2State.value = password
    }

    private fun processResult(result: Result<AuthData>) {
        when (result) {
            is Result.Success -> {
                _screenState.value = RegScreenState.Successful
                viewModelScope.launch {
                    appInteractor.saveAuthDataValue(StorageKey.AUTHDATA, result.data)
                    appInteractor.removeKey(StorageKey.PINCODE)
                    appInteractor.removeKey(StorageKey.PINCODEMISTAKES)
                }
            }

            is Result.Error -> {
                _screenState.value = RegScreenState.ErrorState(
                    emailLengthError = false,
                    emailConsistError = false,
                    passEmptyError = false,
                    passLengthError = false,
                    passDiffError = false
                )
            }
        }
    }
}