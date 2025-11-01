package com.example.myapplication.debitcards.domain.models

data class DebitCardTerms(
    val cashback: Double,
    val maxCount: Int,
    val serviceCost: Long
)