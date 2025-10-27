package com.example.myapplication.carddetails.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.carddetails.domain.models.CardDetailsState
import com.example.myapplication.core.domain.api.CardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CardDetailsViewModel(
    private val cardID: Long,
    private val cardRepository: CardRepository
) : ViewModel() {

    private val _cardDetailsState = MutableStateFlow<CardDetailsState>(value = CardDetailsState.Loading)
    val cardDetailsState: StateFlow<CardDetailsState> = _cardDetailsState.asStateFlow()

    fun requestCardDetail() {
        viewModelScope.launch {
            val cardDetailsResult = cardRepository.getById(cardID)
            when (cardDetailsResult != null) {
                true -> renderState(
                    CardDetailsState.Content(cardDetailsResult)
                )
                false -> renderState(CardDetailsState.Error)
            }
        }
    }

    fun closeCard() {
        viewModelScope.launch {
            cardRepository.remove(cardID)
        }
        renderState(CardDetailsState.Success)
    }

    private fun renderState(state: CardDetailsState) {
        _cardDetailsState.value = state
    }

}