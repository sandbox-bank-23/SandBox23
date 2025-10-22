package com.example.myapplication.deposits.domain.entity

import com.example.myapplication.core.domain.models.Product

data class Deposit(
    val userId: Long,
    val currentDepositNumber: Long,
    val requestNumber: Long,
    val product: Product
)
