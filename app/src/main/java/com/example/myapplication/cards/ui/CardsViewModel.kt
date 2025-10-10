package com.example.myapplication.cards.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CardsViewModel(): ViewModel() {

    private val _cardsState = MutableStateFlow<Boolean>(false)
    val cardsState: StateFlow<Boolean> = _cardsState.asStateFlow()

}