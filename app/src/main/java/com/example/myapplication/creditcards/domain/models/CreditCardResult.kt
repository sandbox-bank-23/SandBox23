package com.example.myapplication.creditcards.domain.models

sealed class CreditCardResult<out T> {
    data class Success<T>(val data: T) : CreditCardResult<T>()
    data class Error(val message: String) : CreditCardResult<Nothing>()
    object NetworkError : CreditCardResult<Nothing>()
    object LimitError : CreditCardResult<Nothing>()
}