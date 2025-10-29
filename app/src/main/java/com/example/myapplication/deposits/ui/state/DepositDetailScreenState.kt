package com.example.myapplication.deposits.ui.state

import com.example.myapplication.deposits.domain.entity.Deposit

sealed class DepositDetailScreenState {
    object Empty: DepositDetailScreenState()
    data class Content(val deposit: Deposit): DepositDetailScreenState()
    data class Error(val message: String?): DepositDetailScreenState()
}