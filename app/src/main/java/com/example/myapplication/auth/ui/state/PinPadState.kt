package com.example.myapplication.auth.ui.state

sealed interface PinPadState {
    data object Loading : PinPadState
    data class PinPad(
        val title: String,
        val digitsEntered: Int,
        val digitsTotal: Int,
        val isError: Boolean,
        val isFingerprintEnabled: Boolean
    ) : PinPadState
}