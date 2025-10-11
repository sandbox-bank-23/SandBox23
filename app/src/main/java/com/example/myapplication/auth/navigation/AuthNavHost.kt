package com.example.myapplication.auth.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.auth.ui.screens.AuthorizationScreen
import com.example.myapplication.auth.ui.screens.RegistrationScreen
import com.example.myapplication.auth.ui.viewmodel.UserViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthNavHost() {
    val navController = rememberNavController()
    val userVm = koinViewModel<UserViewModel>()
    NavHost(
        navController = navController,
        startDestination = AuthScreen.Authorization.route
    ) {
        composable(route = AuthScreen.Authorization.route) {
            AuthorizationScreen(userVm = userVm) {
                navController.navigate(route = AuthScreen.Registration.route)
            }
        }
        composable(route = AuthScreen.Registration.route) {
            RegistrationScreen()
        }
    }
}