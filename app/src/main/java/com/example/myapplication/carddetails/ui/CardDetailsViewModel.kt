package com.example.myapplication.carddetails.ui

import androidx.lifecycle.ViewModel
import com.example.myapplication.carddetails.domain.models.CardDetailsState
import com.example.myapplication.core.domain.models.Card
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CardDetailsViewModel : ViewModel() {

    private val _cardDetailsState = MutableStateFlow<CardDetailsState>(value = CardDetailsState.Content)
    val cardDetailsState: StateFlow<CardDetailsState> = _cardDetailsState.asStateFlow()

    fun closeCard(card: Card) {
        renderState(CardDetailsState.Success)
    }

    private fun renderState(state: CardDetailsState) {
        _cardDetailsState.value = state
    }

}