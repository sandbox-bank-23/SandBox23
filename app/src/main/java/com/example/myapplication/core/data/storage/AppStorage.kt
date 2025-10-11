package com.example.myapplication.core.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

class AppStorage(
    private val dataStore: DataStore<Preferences>
) {
}