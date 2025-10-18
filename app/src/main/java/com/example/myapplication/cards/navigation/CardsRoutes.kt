package com.example.myapplication.cards.navigation

const val CARD_ID = "cardId"
enum class CardsRoutes(val route: String) {
    CARDS_NAVIGATION("cards_navigation"),
    CARDS("cards"),
    CARD_DETAILS("card_details/{$CARD_ID}"),
    CARD_DEBIT("card_debit"),
    CARD_CREDIT("card_credit"),
}