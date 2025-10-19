package com.example.myapplication.creditcards.domain.models

interface CreditCardsState {
    data object Offline : CreditCardsState
    data object Online : CreditCardsState
    data object Success : CreditCardsState
    data object Error : CreditCardsState
    data object Content : CreditCardsState
}