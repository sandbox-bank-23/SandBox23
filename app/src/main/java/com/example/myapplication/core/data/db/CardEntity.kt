package com.example.myapplication.core.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class CardEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long,
    val cvv: Long,
    val endDate: String,
    val owner: String,
    val type: String,
    val percent: Double,
    val balance: Long
)
