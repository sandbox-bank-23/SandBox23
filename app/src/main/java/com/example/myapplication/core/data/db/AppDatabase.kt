package com.example.myapplication.core.data.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapplication.core.data.db.converter.Converters
import com.example.myapplication.core.data.db.dao.LoanDao
import com.example.myapplication.core.data.db.dao.UserDao
import com.example.myapplication.core.data.db.entity.LoanEntity
import com.example.myapplication.core.data.db.entity.UserEntity
import com.example.myapplication.deposits.data.db.DepositDao
import com.example.myapplication.deposits.data.db.DepositEntity

private const val OLD = 4
private const val NEW = 5

@Database(
    version = NEW,
    entities = [
        CardEntity::class,
        UserEntity::class,
        LoanEntity::class,
        DepositEntity::class
    ],
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = OLD, to = NEW)
    ]
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cardDao(): CardDao

    abstract fun depositDao(): DepositDao

    abstract fun userDao(): UserDao

    abstract fun loanDao(): LoanDao

    companion object {
        val MIGRATION_4_5 = object : Migration(OLD, NEW) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE loans ADD COLUMN monthPay TEXT NOT NULL DEFAULT '0'")
            }
        }
    }
}
