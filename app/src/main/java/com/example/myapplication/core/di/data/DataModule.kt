package com.example.myapplication.core.di.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.example.myapplication.core.data.repo.AppRepositoryImpl
import com.example.myapplication.core.data.storage.AppStorage
import com.example.myapplication.core.domain.api.AppRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import androidx.datastore.preferences.core.Preferences

private const val APP_STORAGE = "app_storage"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = APP_STORAGE
)

val coreDataModule = module {
    single<AppRepository> {
        AppRepositoryImpl(get())
    }

    single<AppStorage> { AppStorage(dataStore = androidContext().dataStore) }
}