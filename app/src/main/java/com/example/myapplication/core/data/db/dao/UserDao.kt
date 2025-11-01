package com.example.myapplication.core.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.core.data.db.entity.UserEntity

@Dao
interface UserDao {
    @Insert
    suspend fun createUser(userEntity: UserEntity)

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<UserEntity>

    @Query("DELETE FROM users")
    suspend fun deleteAll()
}