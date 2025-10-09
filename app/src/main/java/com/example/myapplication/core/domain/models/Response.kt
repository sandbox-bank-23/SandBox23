package com.example.myapplication.core.domain.models


data class Response(
    val code: Int,
    val description: String,
    val response: String?
)
