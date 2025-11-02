package com.example.myapplication.core.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class DomainResponse(
    val code: Int,
    val description: String,
    val response: String?
)