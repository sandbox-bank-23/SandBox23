package com.example.myapplication.deposits.domain

sealed class DepositResult<out T> {
    data class Success<T>(val data: T) : DepositResult<T>()
    data class Empty(val message: String?) : DepositResult<Nothing>()
    data class NetworkError(val message: String?) : DepositResult<Nothing>()
    data class DataBaseError(val message: String?) : DepositResult<Nothing>()
    data class InputError(val message: String?) : DepositResult<Nothing>()
}