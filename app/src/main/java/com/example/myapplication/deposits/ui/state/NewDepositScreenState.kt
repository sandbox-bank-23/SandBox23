package com.example.myapplication.deposits.ui.state

import com.example.myapplication.deposits.domain.entity.Deposit


sealed class NewDepositScreenState {

    data object Loading : NewDepositScreenState()

    data class Content(
        val deposits: Deposit
    ) : NewDepositScreenState()

    data object Empty : NewDepositScreenState()

    data class Error(
        val message: String?
    ) : NewDepositScreenState()
}