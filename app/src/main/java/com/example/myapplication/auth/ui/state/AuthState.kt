package com.example.myapplication.auth.ui.state

sealed interface AuthState {
    object IsAuthorized : AuthState
    object IsUnauthorized : AuthState
}