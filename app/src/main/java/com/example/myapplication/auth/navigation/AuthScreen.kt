package com.example.myapplication.auth.navigation

sealed class AuthScreen(val route: String) {
    object Authorization : AuthScreen(route = "authorization")
    object PinPadCreate : AuthScreen(route = "pinpadCreate")
    object PinPadValidate : AuthScreen(route = "pinpadValidate")
    object Registration : AuthScreen(route = "registration")
}