package com.example.myapplication.loansanddeposits.navigation

import com.example.myapplication.core.ui.state.Routes

const val LOAN_ID = "loanId"
const val DEPOSIT_ID = "depositId"

enum class LoansDepositsRoutes(val route: String) {
    FINANCE_NAVIGATION(Routes.FINANCE.route),
    LOANS_DEPOSITS("loans_deposits"),
    OPEN_LOAN("open_loan"),
    OPEN_DEPOSIT("open_deposit"),
    LOAN_DETAILS("loan_details/{$LOAN_ID}"),
    DEPOSIT_DETAILS("deposit_details/{$DEPOSIT_ID}"),

}