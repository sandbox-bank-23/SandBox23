package com.example.myapplication.cards.domain.models

sealed interface CardsState {
    data object Empty : CardsState
    data object Cards : CardsState
}