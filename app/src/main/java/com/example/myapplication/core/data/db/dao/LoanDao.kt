package com.example.myapplication.core.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.core.data.db.entity.LoanEntity
import com.example.myapplication.core.data.db.entity.UserWithLoans
import kotlinx.coroutines.flow.Flow

@Dao
interface LoanDao {
    @Insert
    suspend fun create(loan: LoanEntity): Long

    suspend fun make(loan: LoanEntity): LoanEntity {
        return loan.copy(id = create(loan))
    }

    @Query("SELECT * FROM loans WHERE id = :loanId")
    fun getLoan(loanId: Long): Flow<LoanEntity>

    @Query("SELECT * FROM loans WHERE userId = :userId AND isClose != 1 ORDER BY id DESC")
    fun getLoanList(userId: Long): Flow<List<LoanEntity>>

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserWithLoans(userId: Long): UserWithLoans?

    @Update
    suspend fun close(loan: LoanEntity)

    suspend fun getCloseLoan(loanEntity: LoanEntity): Flow<LoanEntity> {
        close(loan = loanEntity)
        return getLoan(loanId = loanEntity.id)
    }

    @Query("DELETE FROM loans")
    suspend fun deleteAll()
}