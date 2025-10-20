package com.example.myapplication.core.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.myapplication.core.data.db.entity.UserEntity

@Dao
interface UserDao {
    @Insert
    suspend fun createUser(userEntity: UserEntity)
}