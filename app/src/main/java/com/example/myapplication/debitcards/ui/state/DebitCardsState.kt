package com.example.myapplication.debitcards.ui.state

import com.example.myapplication.debitcards.domain.models.DebitCardTerms

sealed interface DebitCardsState {
    data object Error : DebitCardsState
    data object Success : DebitCardsState
    data object Loading : DebitCardsState
    data object Limit : DebitCardsState
    data class Content(val debitCardTerms: DebitCardTerms) : DebitCardsState
}