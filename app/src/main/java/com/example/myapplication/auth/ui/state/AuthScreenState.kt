package com.example.myapplication.auth.ui.state

sealed class AuthScreenState {
    object Default : AuthScreenState()
    data class ErrorState(
        val emailLengthError: Boolean,
        val emailConsistError: Boolean,
        val passEmptyError: Boolean,
        val passLengthError: Boolean
    ) : AuthScreenState()

    object Successful : AuthScreenState()
}
