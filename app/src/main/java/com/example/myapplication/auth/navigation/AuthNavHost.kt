package com.example.myapplication.auth.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.auth.ui.screens.AuthorizationScreen
import com.example.myapplication.auth.ui.screens.RegistrationScreen
import com.example.myapplication.auth.ui.viewmodel.UserViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthNavHost(padding: PaddingValues) {
    val navController = rememberNavController()
    val userVm = koinViewModel<UserViewModel>()
    NavHost(
        navController = navController,
        startDestination = AuthScreen.Authorization.route
    ) {
        composable(route = AuthScreen.Authorization.route) {
            AuthorizationScreen(
                modifier = Modifier.padding(padding),
                userVm = userVm
            ) {
                navController.navigate(route = AuthScreen.Registration.route)
            }
        }
        composable(route = AuthScreen.Registration.route) {
            RegistrationScreen(
                modifier = Modifier.padding(padding),
            )
        }
    }
}

@Composable
fun Auth() {
    Scaffold { paddingValues ->
        AuthNavHost(padding = paddingValues)
    }
}