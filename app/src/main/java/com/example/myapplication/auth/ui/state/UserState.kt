package com.example.myapplication.auth.ui.state

sealed interface UserState {
    object IsAuthorized : UserState
    object IsUnauthorized : UserState
}