package com.example.myapplication.core.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.core.data.db.entity.LoanEntity

@Dao
interface LoanDao {
    @Insert
    suspend fun create(loan: LoanEntity): Long

    suspend fun make(loan: LoanEntity): LoanEntity {
        return loan.copy(id = create(loan))
    }

    @Query("SELECT * FROM loanentity WHERE id = :loanId")
    suspend fun getLoan(loanId: Long): LoanEntity

    @Update
    suspend fun close(loan: LoanEntity)
}