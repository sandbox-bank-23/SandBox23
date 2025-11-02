package com.example.myapplication.profile.domain.interactor

import com.example.myapplication.core.data.network.Response
import kotlinx.coroutines.flow.Flow

interface UpdatesUseCase {
    suspend fun isLatestVersion(): Flow<Response>
}