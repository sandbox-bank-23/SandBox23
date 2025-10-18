package com.example.myapplication.carddetails.domain.models

sealed interface CardDetailsState {
    data object Offline : CardDetailsState
    data object Online : CardDetailsState
    data object Success : CardDetailsState
    data object Content : CardDetailsState
}