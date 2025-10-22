package com.example.myapplication.core.data.network

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val code: Int,
    val description: String,
    val response: String?
)