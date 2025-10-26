package com.example.myapplication.cards.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.myapplication.carddetails.ui.CardDetailsScreen
import com.example.myapplication.carddetails.ui.CardDetailsViewModel
import com.example.myapplication.cards.ui.CardsScreen
import com.example.myapplication.creditcards.ui.CreditCardsScreen
import com.example.myapplication.debitcards.ui.DebitCardsScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

fun NavGraphBuilder.cardsScreenNavigation(navController: NavHostController) {
    navigation(
        startDestination = CardsRoutes.CARDS.route,
        route = CardsRoutes.CARDS_NAVIGATION.route
    ) {
        composable(CardsRoutes.CARDS.route) { CardsScreen(navController) }
        composable(
            CardsRoutes.CARD_DEBIT.route,
            arguments = listOf(
                navArgument(USER_ID) {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getLong(CARD_ID) ?: 0L
            DebitCardsScreen(
                navController = navController,
                userId = userId
            )
        }
        composable(CardsRoutes.CARD_CREDIT.route) { CreditCardsScreen(navController) }
        composable(
            CardsRoutes.CARD_DETAILS.route,
            arguments = listOf(
                navArgument(CARD_ID) {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val cardId = backStackEntry.arguments?.getLong(CARD_ID) ?: 0L
            CardDetailsScreen(
                navController = navController,
                viewModel = koinViewModel<CardDetailsViewModel>(
                    parameters = { parametersOf(cardId) }
                )
            )
        }
    }
}