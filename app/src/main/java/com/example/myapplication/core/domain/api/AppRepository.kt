package com.example.myapplication.core.domain.api

import com.example.myapplication.auth.domain.model.AuthData
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun getBooleanValue(storageKey: StorageKey<Boolean>): Flow<Boolean>
    suspend fun getStringValue(storageKey: StorageKey<String>): Flow<String>
    suspend fun getIntValue(storageKey: StorageKey<Int>): Flow<Int>
    suspend fun getAuthDataValue(storageKey: StorageKey<String>): Flow<AuthData?>
    suspend fun saveBooleanValue(storageKey: StorageKey<Boolean>, value: Boolean)
    suspend fun saveStringValue(storageKey: StorageKey<String>, value: String)
    suspend fun saveIntValue(storageKey: StorageKey<Int>, value: Int)
    suspend fun saveAuthDataValue(storageKey: StorageKey<String>, value: AuthData)
    suspend fun <T> removeKey(storageKey: StorageKey<T>)
    suspend fun clearAll()
    suspend fun getUserId(): String
}