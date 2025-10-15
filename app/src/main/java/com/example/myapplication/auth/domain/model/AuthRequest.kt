package com.example.myapplication.auth.domain.model

data class AuthRequest(
    val email: String,
    val password: String
)