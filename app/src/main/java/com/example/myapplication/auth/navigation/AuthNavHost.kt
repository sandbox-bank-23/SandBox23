package com.example.myapplication.auth.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.auth.ui.screens.AuthorizationScreen
import com.example.myapplication.auth.ui.screens.PinPadScreen
import com.example.myapplication.auth.ui.screens.RegistrationScreen
import com.example.myapplication.auth.ui.state.PinPadMode
import com.example.myapplication.auth.ui.viewmodel.AuthorizationViewModel
import com.example.myapplication.auth.ui.viewmodel.PinPadViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthNavHost(padding: PaddingValues) {
    val navController = rememberNavController()
    val authVm = koinViewModel<AuthorizationViewModel>()
    val pinPadVm = koinViewModel<PinPadViewModel>()
    NavHost(
        navController = navController,
        startDestination = AuthScreen.PinPadValidate.route
    ) {
        composable(route = AuthScreen.Authorization.route) {
            AuthorizationScreen(
                modifier = Modifier.padding(padding),
                authVm = authVm,
                navController = navController
            )
        }
        composable(route = AuthScreen.PinPadCreate.route) {
            PinPadScreen(
                modifier = Modifier.padding(padding),
                pinPadVm = pinPadVm,
                navController = navController,
                pinPadMode = PinPadMode.Create
            )
        }
        composable(route = AuthScreen.PinPadValidate.route) {
            PinPadScreen(
                modifier = Modifier.padding(padding),
                pinPadVm = pinPadVm,
                navController = navController,
                pinPadMode = PinPadMode.Validate
            )
        }
        composable(route = AuthScreen.Registration.route) {
            RegistrationScreen(
                modifier = Modifier.padding(padding),
                navController = navController
            )
        }
    }
}

@Composable
fun Auth() {
    Scaffold(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest) { paddingValues ->
        AuthNavHost(padding = paddingValues)
    }
}