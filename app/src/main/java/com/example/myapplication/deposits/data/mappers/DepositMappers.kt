package com.example.myapplication.deposits.data.mappers

import com.example.myapplication.core.domain.models.Product
import com.example.myapplication.deposits.data.db.DepositEntity
import com.example.myapplication.deposits.domain.entity.Deposit

fun DepositEntity.toDomain(): Deposit = Deposit(
    userId = userId,
    currentDepositNumber = currentDepositNumber,
    requestNumber = requestNumber,
    product = Product(
        id = productId,
        type = type,
        percentType = percentType,
        period = period,
        percent = percent.toLong(),
        balance = balance
    )
)

fun Deposit.toEntity(): DepositEntity = DepositEntity(
    userId = userId,
    currentDepositNumber = currentDepositNumber,
    requestNumber = requestNumber,
    productId = product.id,
    type = product.type,
    percentType = product.percentType,
    period = product.period,
    percent = product.percent.toInt(),
    balance = product.balance
)
