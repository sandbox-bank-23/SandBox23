package com.example.myapplication.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication.core.data.db.converter.Converters
import com.example.myapplication.core.data.db.dao.LoanDao
import com.example.myapplication.core.data.db.dao.UserDao
import com.example.myapplication.core.data.db.entity.LoanEntity
import com.example.myapplication.core.data.db.entity.UserEntity
import com.example.myapplication.deposits.data.db.DepositDao
import com.example.myapplication.deposits.data.db.DepositEntity

@Database(
    version = 3,
    entities = [
        CardEntity::class,
        UserEntity::class,
        LoanEntity::class,
        DepositEntity::class
    ]
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cardDao(): CardDao

    abstract fun depositDao(): DepositDao

    abstract fun userDao(): UserDao

    abstract fun loanDao(): LoanDao
}
