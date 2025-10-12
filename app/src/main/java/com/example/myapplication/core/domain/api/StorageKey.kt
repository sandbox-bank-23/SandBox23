package com.example.myapplication.core.domain.api

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

sealed class StorageKey<T>(val key: Preferences.Key<T>) {
    data object THEME_KEY : StorageKey<Boolean>(booleanPreferencesKey("theme_key"))
    data object LANGUAGE_KEY : StorageKey<String>(stringPreferencesKey("language_key"))
    data object NOTIFICATIONS_KEY : StorageKey<Boolean>(booleanPreferencesKey("notifications_key"))
    data object BIOMETRIC_KEY : StorageKey<Boolean>(booleanPreferencesKey("biometric_key"))
}