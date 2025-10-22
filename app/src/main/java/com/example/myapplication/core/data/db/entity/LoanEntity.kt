package com.example.myapplication.core.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "loans")
data class LoanEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long,
    val type: String,
    val balance: BigDecimal,
    val period: Long,
    val start: Long,
    val end: Long? = null,
    val percent: Long? = null,
    val isClose: Boolean? = null,
)