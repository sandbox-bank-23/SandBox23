package com.example.myapplication.core.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity
data class LoanEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val sum: BigDecimal
)
