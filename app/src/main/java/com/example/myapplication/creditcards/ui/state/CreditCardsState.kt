package com.example.myapplication.creditcards.ui.state

import com.example.myapplication.creditcards.domain.models.CreditCardTerms

interface CreditCardsState {
    data object Error : CreditCardsState
    data object Online : CreditCardsState
    data object Success : CreditCardsState
    data object Loading : CreditCardsState
    data object Limit : CreditCardsState
    data class Content(val creditCardTerms: CreditCardTerms) : CreditCardsState
}