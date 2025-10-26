package com.example.myapplication.creditcards.ui.state

interface CreditCardsState {
    data object Error : CreditCardsState
    data object Online : CreditCardsState
    data object Success : CreditCardsState
    data object Loading : CreditCardsState
    data object Limit : CreditCardsState
    data object Content : CreditCardsState
}