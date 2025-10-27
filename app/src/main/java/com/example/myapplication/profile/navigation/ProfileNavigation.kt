package com.example.myapplication.profile.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.myapplication.core.ui.state.Routes
import com.example.myapplication.profile.ui.ProfileScreen

fun NavGraphBuilder.profileScreenNavigation(navController: NavHostController) {
    navigation(
        startDestination = Routes.PROFILE.route,
        route = Routes.PROFILE_NAVIGATION.route
    ) {
        composable(Routes.PROFILE.route) {
            ProfileScreen(navController)
        }
    }
}