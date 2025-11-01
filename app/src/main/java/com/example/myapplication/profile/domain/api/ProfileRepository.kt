package com.example.myapplication.profile.domain.api

import com.example.myapplication.core.data.network.Response
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun isLatestVersion(): Flow<Response>

    suspend fun changeTheme()

    fun getTheme(): Flow<Boolean>

    suspend fun clearAppData()
}