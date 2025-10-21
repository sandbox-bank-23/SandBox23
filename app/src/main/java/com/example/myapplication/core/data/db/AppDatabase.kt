package com.example.myapplication.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.deposits.data.db.DepositDao
import com.example.myapplication.deposits.data.db.DepositEntity

@Database(
    version = 1,
    entities = [
        CardEntity::class,
        DepositEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cardDao(): CardDao
    abstract fun depositDao(): DepositDao
}
