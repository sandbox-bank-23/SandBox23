package com.example.myapplication.core.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.core.data.db.entity.DepositEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DepositDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertDeposit(deposit: DepositEntity)

    @Delete
    suspend fun deleteDeposit(card: DepositEntity)

    @Query("SELECT * FROM deposits")
    suspend fun getAllDeposits(): List<DepositEntity>

    @Query("SELECT * FROM deposits WHERE id = :id")
    suspend fun getDepositById(id: Long): DepositEntity?

    @Query("SELECT * FROM deposits ORDER BY id ASC")
    fun observeAll(): Flow<List<DepositEntity>>

    @Query("DELETE FROM deposits")
    suspend fun deleteAll()
}