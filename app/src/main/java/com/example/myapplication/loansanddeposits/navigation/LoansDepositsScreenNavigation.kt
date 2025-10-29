package com.example.myapplication.loansanddeposits.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.myapplication.deposits.ui.screens.DepositDetailScreen
import com.example.myapplication.deposits.ui.screens.NewDepositScreen
import com.example.myapplication.loans.ui.screen.CreateLoanScreen
import com.example.myapplication.loansanddeposits.ui.screen.LoansDepositsScreen

fun NavGraphBuilder.loansDepositsScreenNavigation(navController: NavHostController) {
    navigation(
        startDestination = LoansDepositsRoutes.LOANS_DEPOSITS.route,
        route = LoansDepositsRoutes.FINANCE_NAVIGATION.route,
    ) {
        composable(LoansDepositsRoutes.LOANS_DEPOSITS.route) {
            LoansDepositsScreen(
                navController = navController,
//                onApplyCreditClick = { navController.navigate(LoansDepositsRoutes.OPEN_LOAN.route) },
            )
        }

        composable(LoansDepositsRoutes.OPEN_LOAN.route) {
            CreateLoanScreen(
                navController = navController,
            )
        }

        composable(LoansDepositsRoutes.OPEN_DEPOSIT.route) {
            NewDepositScreen(navController = navController)
        }

        composable(
            route = LoansDepositsRoutes.LOAN_DETAILS.route,
            arguments = listOf(navArgument(LOAN_ID) { type = NavType.LongType })
        ) { backStackEntry ->
            // LoanDetailsScreen(loanId = requireNotNull(it.arguments?.getLong(LOAN_ID)))
        }

        composable(
            route = LoansDepositsRoutes.DEPOSIT_DETAILS.route,
            arguments = listOf(navArgument(DEPOSIT_ID) { type = NavType.LongType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getLong(DEPOSIT_ID) ?: 0L
            DepositDetailScreen(
                navController = navController,
                id = itemId
            )
        }
    }
}
