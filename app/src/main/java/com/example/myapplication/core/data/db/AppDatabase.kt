package com.example.myapplication.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [CardEntity::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun cardDao(): CardDao

}