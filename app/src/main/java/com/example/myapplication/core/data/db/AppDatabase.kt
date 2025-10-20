package com.example.myapplication.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.core.data.db.dao.UserDao
import com.example.myapplication.core.data.db.entity.UserEntity

@Database(
    version = 2, entities = [
        CardEntity::class,
        UserEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cardDao(): CardDao

    abstract fun userDao(): UserDao
}