package com.example.myapplication.core.domain.api

import kotlinx.coroutines.flow.Flow

interface AppInteractor {
    suspend fun getBooleanValue(storageKey: StorageKey<Boolean>): Flow<Boolean>
    suspend fun getStringValue(storageKey: StorageKey<String>): Flow<String>
    suspend fun saveStringValue(storageKey: StorageKey<Boolean>, value: Boolean)
    suspend fun saveBooleanValue(storageKey: StorageKey<String>, value: String)
    suspend fun <T> removeKey(storageKey: StorageKey<T>)
    suspend fun clearAll()

}