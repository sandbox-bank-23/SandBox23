package com.example.myapplication.core.data.repo

import androidx.datastore.core.DataStore
import com.example.myapplication.core.data.storage.AppStorage
import com.example.myapplication.core.domain.api.AppRepository
import java.util.prefs.Preferences

class AppRepositoryImpl(
    val appStorage: AppStorage
): AppRepository {

}
