package com.example.myapplication.cards.ui

import com.example.myapplication.core.domain.models.Card

sealed interface CardsState {
    data object Empty : CardsState
    data class Cards(val cards: List<Card>) : CardsState
}