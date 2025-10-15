package com.example.myapplication.core.data.storage

import android.content.Context
import android.content.res.Configuration
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.myapplication.R
import com.example.myapplication.auth.domain.model.AuthData
import com.example.myapplication.core.domain.api.StorageKey
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppStorage(
    private val gson: Gson,
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
                    else -> throw IllegalArgumentException(context.getString(R.string.unsupported_key, storageKey))
                }
            }
    }

    fun getStringValue(storageKey: StorageKey<String>): Flow<String> {
        return dataStore.data
            .map { preferences ->
                when (storageKey) {
                    StorageKey.LANGUAGE -> preferences[storageKey.key] ?: currentLanguage()
                    StorageKey.PINCODE -> preferences[storageKey.key] ?: ""
                    else -> throw IllegalArgumentException(context.getString(R.string.unsupported_key, storageKey))
                }
            }
    }

    fun getIntValue(storageKey: StorageKey<Int>): Flow<Int> {
        return dataStore.data
            .map { preferences ->
                preferences[storageKey.key] ?: when (storageKey) {
                    StorageKey.PINCODEMISTAKES -> 0
                    else -> throw IllegalArgumentException(context.getString(R.string.unsupported_key, storageKey))
                }
            }
    }

    fun getAuthDataValue(storageKey: StorageKey<String>): Flow<AuthData?> {
        return dataStore.data
            .map { preferences ->
                when (storageKey) {
                    StorageKey.AUTHDATA -> {
                        try {
                            gson.fromJson(preferences[storageKey.key], AuthData::class.java)
                        } catch (_: Exception) {
                            null
                        }
                    }
                    else -> throw IllegalArgumentException(context.getString(R.string.unsupported_key, storageKey))
                }
            }
    }

    suspend fun saveBooleanValue(storageKey: StorageKey<Boolean>, value: Boolean) {
        saveValue(storageKey, value)
    }

    suspend fun saveStringValue(storageKey: StorageKey<String>, value: String) {
        saveValue(storageKey, value)
    }

    suspend fun saveIntValue(storageKey: StorageKey<Int>, value: Int) {
        saveValue(storageKey, value)
    }

    suspend fun saveAuthDataValue(storageKey: StorageKey<String>, value: AuthData) {
        val jsonAuthData = gson.toJson(value)
        saveValue(storageKey, jsonAuthData)
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