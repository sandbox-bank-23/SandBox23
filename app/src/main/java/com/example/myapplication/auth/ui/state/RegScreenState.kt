package com.example.myapplication.auth.ui.state

sealed class RegScreenState {
    object Default : RegScreenState()
    data class ErrorState(
        val emailLengthError: Boolean,
        val emailConsistError: Boolean,
        val passEmptyError: Boolean,
        val passLengthError: Boolean,
        val passDiffError: Boolean
    ) : RegScreenState()

    object Successful : RegScreenState()
}