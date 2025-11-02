package com.example.myapplication.debitcards.domain.models

sealed class DebitCardResult<out T> {
    data class Success<T>(val data: T) : DebitCardResult<T>()
    data class Error(val message: String) : DebitCardResult<Nothing>()
    object NetworkError : DebitCardResult<Nothing>()
    object LimitError : DebitCardResult<Nothing>()
}