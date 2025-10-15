package com.example.myapplication.auth.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.auth.domain.AuthInteractor
import com.example.myapplication.auth.domain.model.AuthData
import com.example.myapplication.auth.domain.model.AuthRequest
import com.example.myapplication.auth.domain.state.Result
import com.example.myapplication.core.domain.api.AppInteractor
import com.example.myapplication.core.domain.api.StorageKey
import com.example.myapplication.core.ui.model.TextInputState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val appInteractor: AppInteractor,
    private val authInteractor: AuthInteractor,
    private val application: Application
) : ViewModel() {

    // StateFlow для логина
    private val _loginState = MutableStateFlow(
        TextInputState(label = application.applicationContext.getString(R.string.email))
    )
    val loginState: StateFlow<TextInputState> = _loginState.asStateFlow()

    // StateFlow для пароля
    private val _passwordState = MutableStateFlow(
        TextInputState(
            label = application.applicationContext.getString(R.string.password),
            supportingText = application.applicationContext.getString(R.string.pass_constraint),
            isPassword = true
        )
    )
    val passwordState: StateFlow<TextInputState> = _passwordState.asStateFlow()

    // StateFlow для еще раз пароля
    private val _password2State = MutableStateFlow(
        TextInputState(
            label = application.applicationContext.getString(R.string.repeat_password),
            supportingText = application.applicationContext.getString(R.string.pass_constraint),
            isPassword = true
        )
    )
    val password2State: StateFlow<TextInputState> = _password2State.asStateFlow()

    // StateFlow для перехода на экран создания пин-кода
    private val _pinCodeCreateTriggerState = MutableStateFlow(false)
    val pinCodeCreateTriggerState = _pinCodeCreateTriggerState.asStateFlow()

    fun register() {
        // Сначала проверяем, что первый и второй пароль одинаковы
        if (_passwordState.value.valueText != _password2State.value.valueText) {
            _password2State.value = _password2State.value.copy(
                isError = true,
                isSuccess = false,
                supportingText = application.applicationContext.getString(
                    R.string.passwords_are_different
                )
            )
            return
        }

        // Теперь валидируем логин и пароль на основе содержимого, без запроса бэкенда
        val emailCheck = authInteractor.isEmailValid(_loginState.value.valueText)
        val passwordCheck = authInteractor.isPasswordValid(
            _passwordState.value.valueText
        )
        if (!emailCheck.first) {
            _loginState.value = _loginState.value.copy(
                isError = true,
                isSuccess = false,
                supportingText = emailCheck.second
            )
        }
        if (!passwordCheck.first) {
            _passwordState.value = _passwordState.value.copy(
                isError = true,
                isSuccess = false,
                supportingText = passwordCheck.second
            )
        }
        if (!emailCheck.first || !passwordCheck.first) return

        // Затем отправляем данные на регистрацию на бэкенде
        val registerRequest = AuthRequest(
            _loginState.value.valueText,
            _passwordState.value.valueText
        )
        viewModelScope.launch {
            processResult(authInteractor.register(registerRequest))
        }
    }

    fun onLoginChange(login: String) {
        _loginState.value = _loginState.value.copy(
            valueText = login,
            isError = false,
            isSuccess = false,
            supportingText = ""
        )
    }

    fun onPasswordChange(password: String) {
        _passwordState.value = _passwordState.value.copy(
            valueText = password,
            isError = false,
            isSuccess = false,
            supportingText = application.applicationContext.getString(R.string.pass_constraint)
        )
    }

    fun onPassword2Change(password: String) {
        _password2State.value = _password2State.value.copy(
            valueText = password,
            isError = false,
            isSuccess = false,
            supportingText = application.applicationContext.getString(R.string.pass_constraint)
        )
    }

    private fun processResult(result: Result<AuthData>) {
        when (result) {
            is Result.Success -> {
                // Если мы здесь, то регистрация прошла успешно, сохраняем полученные токены,
                // удаляем старый пин-код и открываем экран создания пин-кода
                _loginState.value = _loginState.value.copy(
                    isError = false,
                    isSuccess = true
                )
                _passwordState.value = _passwordState.value.copy(
                    isError = false,
                    isSuccess = true
                )
                _password2State.value = _password2State.value.copy(
                    isError = false,
                    isSuccess = true
                )
                viewModelScope.launch {
                    appInteractor.saveAuthDataValue(StorageKey.AUTHDATA, result.data)
                    appInteractor.removeKey(StorageKey.PINCODE)
                    appInteractor.removeKey(StorageKey.PINCODEMISTAKES)
                    _pinCodeCreateTriggerState.value = true
                }
            }
            is Result.Error -> {
                // Очищаем пароль, выставляем ошибку на логине
                _loginState.value = _loginState.value.copy(
                    isError = true,
                    isSuccess = false,
                    supportingText = result.message.orEmpty()
                )
                _passwordState.value = _passwordState.value.copy(valueText = "")
                _password2State.value = _password2State.value.copy(valueText = "")
            }
        }
    }
}