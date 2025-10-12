package com.example.myapplication.core.domain.impl

import com.example.myapplication.core.domain.api.AppInteractor
import com.example.myapplication.core.domain.api.AppRepository
import com.example.myapplication.core.domain.api.StorageKey
import kotlinx.coroutines.flow.Flow

class AppInteractorImpl(
    val appRepository: AppRepository
) : AppInteractor {
    override suspend fun getBooleanValue(storageKey: StorageKey<Boolean>): Flow<Boolean> {
        return appRepository.getBooleanValue(storageKey)
    }

    override suspend fun getStringValue(storageKey: StorageKey<String>): Flow<String> {
        return appRepository.getStringValue(storageKey)
    }

    override suspend fun saveStringValue(
        storageKey: StorageKey<Boolean>,
        value: Boolean
    ) {
        appRepository.saveBooleanValue(storageKey, value)
    }

    override suspend fun saveBooleanValue(
        storageKey: StorageKey<String>,
        value: String
    ) {
        appRepository.saveStringValue(storageKey, value)
    }

    override suspend fun <T> removeKey(storageKey: StorageKey<T>) {
        appRepository.removeKey(storageKey)
    }

    override suspend fun clearAll() {
        appRepository.clearAll()
    }
}