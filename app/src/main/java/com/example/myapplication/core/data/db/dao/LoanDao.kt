package com.example.myapplication.core.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.myapplication.core.data.db.entity.LoanEntity
import com.example.myapplication.core.data.db.entity.UserWithLoans

@Dao
interface LoanDao {
    @Insert
    suspend fun create(loan: LoanEntity): Long

    suspend fun make(loan: LoanEntity): LoanEntity {
        return loan.copy(id = create(loan))
    }

    @Transaction
    @Query("SELECT * FROM loans WHERE id = :loanId")
    suspend fun getLoan(loanId: Long): LoanEntity

    @Transaction
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserWithLoans(userId: Long): UserWithLoans

    @Update
    suspend fun close(loan: LoanEntity)

    @Transaction
    suspend fun getCloseLoan(loanEntity: LoanEntity): LoanEntity {
        close(loan = loanEntity)
        return getLoan(loanId = loanEntity.id)
    }
}