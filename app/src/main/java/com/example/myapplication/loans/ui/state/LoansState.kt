package com.example.myapplication.loans.ui.state

import com.example.myapplication.loans.data.mock.model.Credit

sealed class LoansState {
    data object Loading : LoansState()
    data class Content(
        val credit: List<Credit>
    ) : LoansState()

    data object NetworkError : LoansState()
    data object LoanApproved : LoansState()
    data object LimitExceeded : LoansState()

    data object Default : LoansState()
}

data class InitialCharacteristics(
    val monthLimit: List<Int>,
    val moneyLimit: List<Int>
)