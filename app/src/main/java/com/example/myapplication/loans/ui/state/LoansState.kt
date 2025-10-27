package com.example.myapplication.loans.ui.state

sealed class LoansState {
    data object NetworkError : LoansState()
    data object LoanApproved : LoansState()
    data object LimitExceeded : LoansState()

    data object Default : LoansState()
}

data class InitialCharacteristics(
    val monthLimit: List<Int>,
    val moneyLimit: List<Int>
)