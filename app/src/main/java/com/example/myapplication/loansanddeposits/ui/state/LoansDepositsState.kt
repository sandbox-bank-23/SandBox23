package com.example.myapplication.loansanddeposits.ui.state

data class DepositUi(
    val id: Long,
    val title: String,
    val balanceText: String,
    val percentType: Int
)

enum class CreditType { Auto, Mortgage }

data class CreditUi(
    val id: Long,
    val name: String,
    val amountText: String,
    val type: CreditType
)

sealed class LoansDepositsState {
    data object Loading : LoansDepositsState()
    data class Content(
        val deposits: List<DepositUi>,
        val credits: List<CreditUi>
    ) : LoansDepositsState()

    data class Error(val message: String) : LoansDepositsState()
    data object Offline : LoansDepositsState()
}