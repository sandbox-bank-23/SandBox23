package com.example.myapplication.deposits.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deposits")
data class DepositEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val userId: Long,
    val currentDepositNumber: Long,
    val requestNumber: Long,

    val productId: Long,
    val type: String,
    val percentType: Long,
    val period: Long,
    val percent: Int,
    val balance: Long
)
