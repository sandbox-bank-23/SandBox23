package com.example.myapplication.core.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChartPay(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val idCredit: Long,
    val datePay: Long,
    val isPay: Boolean,
    val isLate: Boolean
)
