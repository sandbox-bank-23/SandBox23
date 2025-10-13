package com.example.myapplication.auth.domain.model

data class AuthData(
    val code: Int,
    val description: String,
    val response: String?
)