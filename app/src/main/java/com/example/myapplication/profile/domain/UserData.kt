package com.example.myapplication.profile.domain

data class UserData(
    val name: String,
    val id: String,
    val totalBalance: Long,
    val creditCardsBalance: Long,
    val depositsBalance: Long,
    val loansBalance: Long,
)
