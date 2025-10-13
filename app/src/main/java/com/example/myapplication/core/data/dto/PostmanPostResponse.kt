package com.example.myapplication.core.data.dto

import com.example.myapplication.core.data.network.Response
import kotlinx.serialization.Serializable

@Serializable
data class PostmanPostResponse(
    val json: Response
)

