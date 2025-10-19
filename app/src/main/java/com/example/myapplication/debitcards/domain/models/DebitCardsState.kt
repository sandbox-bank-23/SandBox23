package com.example.myapplication.debitcards.domain.models

sealed interface DebitCardsState {
    data object Offline : DebitCardsState
    data object Online : DebitCardsState
    data object Success : DebitCardsState
    data object Error : DebitCardsState
    data object Content : DebitCardsState
}