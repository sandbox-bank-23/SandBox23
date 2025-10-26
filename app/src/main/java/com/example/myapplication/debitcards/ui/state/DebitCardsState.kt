package com.example.myapplication.debitcards.ui.state

sealed interface DebitCardsState {
    data object Error : DebitCardsState
    data object Online : DebitCardsState
    data object Success : DebitCardsState
    data object Loading : DebitCardsState
    data object Limit : DebitCardsState
    data object Content : DebitCardsState
}