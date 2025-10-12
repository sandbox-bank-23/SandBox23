package com.example.myapplication.core.data.repo

import com.example.myapplication.core.data.storage.AppStorage
import com.example.myapplication.core.domain.api.AppRepository
import com.example.myapplication.core.domain.api.StorageKey
import kotlinx.coroutines.flow.Flow

class AppRepositoryImpl(
    val appStorage: AppStorage
) : AppRepository {

    override suspend fun getBooleanValue(storageKey: StorageKey<Boolean>): Flow<Boolean> {
        return appStorage.getBooleanValue(storageKey)
    }

    override suspend fun getStringValue(storageKey: StorageKey<String>): Flow<String> {
        return appStorage.getStringValue(storageKey)
    }

    override suspend fun saveBooleanValue(
        storageKey: StorageKey<Boolean>,
        value: Boolean
    ) {
        appStorage.saveBooleanValue(storageKey, value)
    }

    override suspend fun saveStringValue(
        storageKey: StorageKey<String>,
        value: String
    ) {
        appStorage.saveStringValue(storageKey, value)
    }

    override suspend fun <T> removeKey(storageKey: StorageKey<T>) {
        appStorage.removeKey(storageKey)
    }

    override suspend fun clearAll() {
        appStorage.clearAll()
    }
}
