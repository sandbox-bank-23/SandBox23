package com.example.myapplication.cards.ui

import androidx.lifecycle.ViewModel
import com.example.myapplication.cards.domain.models.CardsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CardsViewModel : ViewModel() {

    private val _cardsState = MutableStateFlow<CardsState>(value = CardsState.Empty)
    val cardsState: StateFlow<CardsState> = _cardsState.asStateFlow()

    fun createCard() {
        renderState(CardsState.Cards)
    }

    private fun renderState(state: CardsState) {
        _cardsState.value = state
    }

}