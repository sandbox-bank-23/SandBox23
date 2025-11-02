package com.example.myapplication.creditcards.domain.models

data class CreditCardTerms(
    val cashback: Double,
    val maxCount: Int,
    val serviceCost: Long,
    val maxCreditLimit: Long
)