package com.example.myapplication.auth.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.auth.domain.AuthInteractor
import com.example.myapplication.auth.domain.model.AuthData
import com.example.myapplication.auth.domain.model.AuthRequest
import com.example.myapplication.auth.ui.state.AuthScreenState
import com.example.myapplication.auth.ui.utils.ValidationUtils
import com.example.myapplication.core.domain.api.AppInteractor
import com.example.myapplication.core.domain.api.StorageKey
import com.example.myapplication.core.domain.models.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthorizationViewModel(
    private val appInteractor: AppInteractor,
    private val authInteractor: AuthInteractor
) : ViewModel() {

    private val _screenState = MutableStateFlow<AuthScreenState>(
        AuthScreenState.Default
    )
    val screenState: StateFlow<AuthScreenState> = _screenState.asStateFlow()

    private var _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private var _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    fun onLoginChange(login: String) {
        _email.value = login
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun authorize() {
        val emailCheck = ValidationUtils.isEmailValid(email.value)
        val passwordCheck = ValidationUtils.isPasswordValid(password.value)

        val isEmailError = emailCheck.first || emailCheck.second
        val isPasswordError = passwordCheck.first || passwordCheck.second

        if (isEmailError || isPasswordError) {
            _screenState.value = AuthScreenState.ErrorState(
                emailLengthError = emailCheck.first,
                emailConsistError = emailCheck.second,
                passEmptyError = passwordCheck.first,
                passLengthError = passwordCheck.second
            )
        } else {
            viewModelScope.launch {
                processResult(
                    authInteractor.login(
                        authRequest = AuthRequest(
                            email = email.value,
                            password = password.value
                        )
                    )
                )
            }
        }
    }

    private fun processResult(result: Result<AuthData>) {
        when (result) {
            is Result.Success -> {
                _screenState.value = AuthScreenState.Successful
                viewModelScope.launch {
                    appInteractor.saveAuthDataValue(StorageKey.AUTHDATA, result.data)
                    appInteractor.removeKey(StorageKey.PINCODE)
                    appInteractor.removeKey(StorageKey.PINCODEMISTAKES)
                }
            }

            is Result.Error -> {
                _screenState.value = AuthScreenState.ErrorState(
                    emailLengthError = false,
                    emailConsistError = false,
                    passEmptyError = false,
                    passLengthError = false
                )
            }
        }
    }
}