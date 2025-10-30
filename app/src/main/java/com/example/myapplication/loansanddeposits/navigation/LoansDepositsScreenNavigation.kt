package com.example.myapplication.loansanddeposits.navigation

import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.myapplication.deposits.ui.screens.DepositDetailScreen
import com.example.myapplication.deposits.ui.screens.NewDepositScreen
import com.example.myapplication.loans.ui.screen.CreateLoanScreen
import com.example.myapplication.loans.ui.screen.LoanDetailScreen
import com.example.myapplication.loans.ui.viewmodel.LoanDetailViewModel
import com.example.myapplication.loansanddeposits.ui.screen.LoansDepositsScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Suppress("LongMethod")
fun NavGraphBuilder.loansDepositsScreenNavigation(navController: NavHostController) {
    navigation(
        startDestination = LoansDepositsRoutes.LOANS_DEPOSITS.route,
        route = LoansDepositsRoutes.FINANCE_NAVIGATION.route,
    ) {
        composable(LoansDepositsRoutes.LOANS_DEPOSITS.route) {
            LoansDepositsScreen(navController = navController)
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
            val loanId = backStackEntry.arguments?.getLong("loanId") ?: 0L

            val viewModel: LoanDetailViewModel = koinViewModel(
                parameters = { parametersOf(loanId) }
            )
            LoanDetailScreen(
                navController = navController,
                onCloseClick = viewModel::onCloseClick,
                loanData = viewModel.loanData.collectAsState().value
            )
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
