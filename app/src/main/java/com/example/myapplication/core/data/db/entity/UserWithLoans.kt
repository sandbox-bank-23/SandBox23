package com.example.myapplication.core.data.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithLoans(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val loans: List<LoanEntity>
)
