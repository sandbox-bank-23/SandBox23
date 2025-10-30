package com.example.myapplication.profile.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.myapplication.core.ui.state.Routes
import com.example.myapplication.profile.ui.screen.ProfileScreen

fun NavGraphBuilder.profileScreenNavigation(navController: NavHostController) {
    composable(Routes.PROFILE.route) {
        ProfileScreen(navController = navController)
    }
}