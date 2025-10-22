package com.example.myapplication.core.ui.model

data class TextInputState(
    val label: String,
    val valueText: String = "",
    val supportingText: String = "",
    val isPassword: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false
)
