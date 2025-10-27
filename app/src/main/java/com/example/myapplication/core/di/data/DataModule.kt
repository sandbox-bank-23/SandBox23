package com.example.myapplication.core.di.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.myapplication.core.data.db.AppDatabase
import com.example.myapplication.core.data.db.CardDao
import com.example.myapplication.core.data.db.dao.LoanDao
import com.example.myapplication.core.data.db.dao.UserDao
import com.example.myapplication.core.data.mappers.CardDbConverter
import com.example.myapplication.core.data.mock.SkyMock
import com.example.myapplication.core.data.repo.AppRepositoryImpl
import com.example.myapplication.core.data.repo.CardRepositoryImpl
import com.example.myapplication.core.data.storage.AppStorage
import com.example.myapplication.core.domain.api.AppRepository
import com.example.myapplication.core.domain.api.CardRepository
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val APP_STORAGE = "app_storage"
private const val DB_NAME = "database.db"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = APP_STORAGE
)

val coreDataModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "sandbox_bank23.db"
        )
//            .fallbackToDestructiveMigration(true)
            .build()
    }

    single<AppRepository> {
        AppRepositoryImpl(get())
    }

    single<UserDao> { get<AppDatabase>().userDao() }

    single<LoanDao> { get<AppDatabase>().loanDao() }

    single<CardDao> { get<AppDatabase>().cardDao() }

    single<AppStorage> {
        AppStorage(
            dataStore = androidContext().dataStore,
            gson = get(),
            context = androidContext()
        )
    }

    single<CardRepository> {
        CardRepositoryImpl(get(), get())
    }

    factory<CardDbConverter> { CardDbConverter() }

    single<AppDatabase> {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration(true)
            .build()
    }

    single<CardRepository> {
        CardRepositoryImpl(get(), get())
    }

    factory<CardDbConverter> { CardDbConverter() }

    single<SkyMock> { SkyMock() }

    factory<Gson> { Gson() }
}