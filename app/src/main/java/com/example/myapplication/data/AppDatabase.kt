package com.example.myapplication.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.db.CardDao
import com.example.myapplication.data.db.CardEntity

@Database(version = 1, entities = [CardEntity::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun cardDao(): CardDao

}