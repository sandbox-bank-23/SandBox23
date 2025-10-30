package com.example.myapplication.profile.domain.api

import com.example.myapplication.core.data.network.Response
import kotlinx.coroutines.flow.Flow

interface UpdatesRepository {
    fun isLatestVersion(): Flow<Response>
}