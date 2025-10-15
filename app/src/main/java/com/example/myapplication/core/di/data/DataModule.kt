package com.example.myapplication.core.di.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.myapplication.core.data.repo.AppRepositoryImpl
import com.example.myapplication.core.data.storage.AppStorage
import com.example.myapplication.core.domain.api.AppRepository
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val APP_STORAGE = "app_storage"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = APP_STORAGE
)

val coreDataModule = module {
    single<AppRepository> {
        AppRepositoryImpl(get())
    }

    single<AppStorage> {
        AppStorage(
            dataStore = androidContext().dataStore,
            gson = get(),
            context = androidContext()
        )
    }

    factory<Gson> { Gson() }
}