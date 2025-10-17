package com.example.myapplication.cards.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.myapplication.carddetails.ui.CardsDetailsScreen
import com.example.myapplication.cards.ui.CardsScreen

fun NavGraphBuilder.cardsScreenNavigation(navController: NavHostController) {
    navigation(startDestination = CardsRoutes.CARDS.route,
        route = CardsRoutes.CARDS_NAVIGATION.route) {
        composable(CardsRoutes.CARDS.route) { CardsScreen(navController) }
        composable(CardsRoutes.CARD_DETAILS.route) { CardsDetailsScreen(navController) }
    }
}