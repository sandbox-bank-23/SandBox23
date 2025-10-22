package com.example.myapplication.carddetails.ui

import androidx.lifecycle.ViewModel
import com.example.myapplication.carddetails.domain.models.CardDetailsState
import com.example.myapplication.core.domain.models.Card
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CardDetailsViewModel(private val cardID: Long) : ViewModel() {

    private val _cardDetailsState = MutableStateFlow<CardDetailsState>(value = CardDetailsState.Loading)
    val cardDetailsState: StateFlow<CardDetailsState> = _cardDetailsState.asStateFlow()

    fun requestCardDetail() {
        // Здесь нужно сходить в репозиторий за картой
        val card = Card(
            id = cardID,
            cvv = 12,
            endDate = "07/2007",
            owner = "Ivanova Oksana",
            type = "Credit",
            percent = 2.5,
            balance = 500_000
        )
        renderState(CardDetailsState.Content(card))
    }

    fun closeCard() {
        renderState(CardDetailsState.Offline)
    }



    private fun renderState(state: CardDetailsState) {
        _cardDetailsState.value = state
    }

}