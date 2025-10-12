package com.example.myapplication.core.data.storage

import android.content.Context
import android.content.res.Configuration
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.myapplication.core.domain.api.StorageKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppStorage(
    private val dataStore: DataStore<Preferences>,
    private val context: Context
) {
    fun getBooleanValue(storageKey: StorageKey<Boolean>): Flow<Boolean> {
        return dataStore.data
            .map { preferences ->
                preferences[storageKey.key] ?: when (storageKey) {
                    StorageKey.THEME -> currentTheme()
                    StorageKey.BIOMETRIC -> false
                    StorageKey.NOTIFICATIONS -> false
                    else -> throw IllegalArgumentException("Unsupported key: $storageKey")
                }
            }
    }

    fun getStringValue(storageKey: StorageKey<String>): Flow<String> {

        return dataStore.data
            .map { preferences ->
                when (storageKey) {
                    StorageKey.LANGUAGE -> preferences[storageKey.key] ?: currentLanguage()
                    else -> throw IllegalArgumentException("Unsupported key: $storageKey")
                }
            }
    }

    suspend fun saveBooleanValue(storageKey: StorageKey<Boolean>, value: Boolean) {
        saveValue(storageKey, value)
    }

    suspend fun saveStringValue(storageKey: StorageKey<String>, value: String) {
        saveValue(storageKey, value)
    }

    suspend fun <T> saveValue(storageKey: StorageKey<T>, value: T) {
        dataStore.edit { preferences ->
            preferences[storageKey.key] = value
        }
    }

    suspend fun <T> removeKey(storageKey: StorageKey<T>) {
        dataStore.edit { preferences ->
            preferences.remove(storageKey.key)
        }
    }

    suspend fun clearAll() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    private fun currentTheme(): Boolean =
        context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES

    private fun currentLanguage(): String {
        val locale = context.resources.configuration.locales[0]
        val languageCode = locale.language
        return languageCode
    }
}