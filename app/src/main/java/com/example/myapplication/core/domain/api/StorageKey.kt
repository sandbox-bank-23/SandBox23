package com.example.myapplication.core.domain.api

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

sealed class StorageKey<T>(val key: Preferences.Key<T>) {
    data object THEME : StorageKey<Boolean>(booleanPreferencesKey("theme_key"))
    data object LANGUAGE : StorageKey<String>(stringPreferencesKey("language_key"))
    data object NOTIFICATIONS : StorageKey<Boolean>(booleanPreferencesKey("notifications_key"))
    data object BIOMETRIC : StorageKey<Boolean>(booleanPreferencesKey("biometric_key"))
    data object AUTHDATA : StorageKey<String>(stringPreferencesKey("authData_key"))
    data object PINCODE : StorageKey<String>(stringPreferencesKey("pinCode_key"))
    data object PINCODEMISTAKES : StorageKey<Int>(intPreferencesKey("pinCodeMistakes_key"))
}