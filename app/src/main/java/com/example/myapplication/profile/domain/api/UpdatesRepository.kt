package com.example.myapplication.profile.domain.api

import com.example.myapplication.core.data.network.Response

interface UpdatesRepository {
    fun isLatestVersion(): Response
}