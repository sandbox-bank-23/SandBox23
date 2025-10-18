package com.example.myapplication.carddetails.domain.models

import com.example.myapplication.core.domain.models.Card

sealed interface CardDetailsState {
    data object Offline : CardDetailsState
    data object Online : CardDetailsState
    data object Success : CardDetailsState
    data class Content(val card: Card) : CardDetailsState
}