package com.example.myapplication.profile.data.repo

import com.example.myapplication.core.data.network.Response
import com.example.myapplication.profile.domain.api.UpdatesRepository
import kotlinx.serialization.json.Json

class UpdatesRepositoryImpl : UpdatesRepository {
    override fun isLatestVersion() = Response(
        code = 200,
        description = "OK",
        response = Json.encodeToString(true)
    )
}