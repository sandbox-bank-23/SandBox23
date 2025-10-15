package com.example.myapplication.auth.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.auth.navigation.AuthScreen
import com.example.myapplication.auth.ui.state.AuthState
import com.example.myapplication.auth.ui.state.PinPadMode
import com.example.myapplication.auth.ui.state.PinPadState
import com.example.myapplication.core.domain.api.AppInteractor
import com.example.myapplication.core.domain.api.StorageKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PinPadViewModel(
    private val appInteractor: AppInteractor,
    private val application: Application
) : ViewModel() {

    // StateFlow для состояния авторизации
    private val _authState = MutableStateFlow<AuthState>(value = AuthState.IsUnauthorized)
    val authState = _authState.asStateFlow()

    // StateFlow для состояния пин-пада
    private val _pinPadState = MutableStateFlow<PinPadState>(value = PinPadState.Loading)
    val pinPadState = _pinPadState.asStateFlow()

    // StateFlow для сообщения об ошибке (Toast)
    private val _errorMessageState = MutableStateFlow("")
    val errorMessageState = _errorMessageState.asStateFlow()

    // StateFlow для дальнейшей навигации
    private val _navState = MutableStateFlow("")
    val navState = _navState.asStateFlow()

    // Переменные общие
    private var savedPinCode = ""
    private var firstPinCode = ""
    private var currentPinCode = ""
    private var currentMode = PinPadMode.Create
    private var numberOfMistakes = 0

    fun updatePinPadMode(pinPadMode: PinPadMode) {
        initVariables()
        currentMode = pinPadMode
        when (pinPadMode) {
            PinPadMode.Create -> {
                renderDigits()
            }
            PinPadMode.Validate -> {
                viewModelScope.launch {
                    appInteractor.getIntValue(StorageKey.PINCODEMISTAKES).collect { pinCodeMistakes ->
                        numberOfMistakes = pinCodeMistakes
                    }
                }
                viewModelScope.launch {
                    val pinCode = appInteractor.getStringValue(StorageKey.PINCODE).first()
                    when (pinCode.isNotEmpty()) {
                        true -> {
                            savedPinCode = pinCode
                            renderDigits()
                        }
                        false -> _navState.value = AuthScreen.Authorization.route
                    }
                }
            }
        }
    }

    fun onDigitClick(digit: Int) {
        currentPinCode = currentPinCode.plus(digit.toString())
        when (currentPinCode.length < PIN_CODE_LENGTH) {
            true -> renderDigits()
            false -> {
                when (currentMode) {
                    PinPadMode.Validate -> checkPinCodeValidate()
                    PinPadMode.Create -> checkPinCodeCreate()
                }
            }
        }
    }

    fun onBackspaceClick() {
        if (currentPinCode.isNotEmpty()) {
            currentPinCode = currentPinCode.dropLast(1)
            renderDigits()
        }
    }

    fun onFingerprintClick() {
        // Обработка нажатия на символ подключения входа по отпечатку пальца
    }

    fun cleanNav() {
        _navState.value = ""
        _pinPadState.value = PinPadState.Loading
    }

    fun cleanErrorMessage() {
        _errorMessageState.value = ""
    }

    private fun initVariables() {
        savedPinCode = ""
        firstPinCode = ""
        currentPinCode = ""
        numberOfMistakes = 0
    }

    private fun checkPinCodeValidate() {
        if (currentPinCode == savedPinCode) {
            saveNumberOfMistakes(0)
            renderDigits()
            _authState.value = AuthState.IsAuthorized
        } else {
            val newNumberOfMistakes = numberOfMistakes + 1
            val triesLeft = PIN_CODE_MAX_TRIES - newNumberOfMistakes
            saveNumberOfMistakes(newNumberOfMistakes)
            if (newNumberOfMistakes >= PIN_CODE_MAX_TRIES) {
                clearAuthData()
                _navState.value = AuthScreen.Authorization.route
            } else {
                val errorMessage = application.applicationContext.resources.getQuantityString(
                    R.plurals.numberOfTries,
                    triesLeft,
                    triesLeft
                )
                currentPinCode = ""
                renderDigits(errorMessage)
            }
        }
    }

    private fun checkPinCodeCreate() {
        if (firstPinCode.isNotEmpty()) {
            if (firstPinCode == currentPinCode) {
                saveNumberOfMistakes(0)
                savePinCode()
                renderDigits()
                _authState.value = AuthState.IsAuthorized
            } else {
                firstPinCode = ""
                currentPinCode = ""
                renderDigits(application.applicationContext.getString(R.string.pincodes_are_different))
            }
        } else {
            firstPinCode = currentPinCode
            currentPinCode = ""
            renderDigits()
        }
    }

    private fun renderDigits(errorMessage: String = "") {
        when (_pinPadState.value) {
            is PinPadState.PinPad -> {
                if (errorMessage.isNotEmpty()) {
                    _pinPadState.value = (_pinPadState.value as PinPadState.PinPad).copy(
                        title = getTitle(),
                        digitsEntered = PIN_CODE_LENGTH,
                        isError = true
                    )
                    _errorMessageState.value = errorMessage
                } else {
                    _pinPadState.value = (_pinPadState.value as PinPadState.PinPad).copy(
                        title = getTitle(),
                        digitsEntered = currentPinCode.length,
                        isError = false
                    )
                }
            }
            is PinPadState.Loading -> {
                _pinPadState.value = PinPadState.PinPad(
                    title = getTitle(),
                    digitsEntered = currentPinCode.length,
                    digitsTotal = PIN_CODE_LENGTH,
                    isError = false,
                    isFingerprintEnabled = false
                )
            }
        }
    }

    private fun getTitle(): String {
        return when (currentMode) {
            PinPadMode.Validate -> application.applicationContext.getString(R.string.enter_pincode)
            PinPadMode.Create -> {
                if (firstPinCode.isNotEmpty()) {
                    application.applicationContext.getString(R.string.repeat_new_pincode)
                } else {
                    application.applicationContext.getString(R.string.enter_new_pincode)
                }
            }

        }
    }

    private fun saveNumberOfMistakes(mistakes: Int) {
        viewModelScope.launch {
            appInteractor.saveIntValue(StorageKey.PINCODEMISTAKES, mistakes)
        }
    }

    private fun savePinCode() {
        viewModelScope.launch {
            appInteractor.saveStringValue(StorageKey.PINCODE, currentPinCode)
        }
    }

    private fun clearAuthData() {
        savedPinCode = ""
        currentPinCode = ""
        viewModelScope.launch {
            appInteractor.removeKey(StorageKey.PINCODEMISTAKES)
        }
        viewModelScope.launch {
            appInteractor.removeKey(StorageKey.PINCODE)
        }
        viewModelScope.launch {
            appInteractor.removeKey(StorageKey.AUTHDATA)
        }
    }

    companion object {
        const val PIN_CODE_LENGTH = 6
        const val PIN_CODE_MAX_TRIES = 5
    }
}