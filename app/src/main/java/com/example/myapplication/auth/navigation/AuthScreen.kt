package com.example.myapplication.auth.navigation

sealed class AuthScreen(val route: String) {
    object Authorization : AuthScreen(route = "authorization")
    object Registration : AuthScreen(route = "registration")
}