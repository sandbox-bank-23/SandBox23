@file:Suppress("MagicNumber")

package com.example.myapplication.profile.data.repo

import com.example.myapplication.core.data.network.Response
import com.example.myapplication.profile.domain.api.UpdatesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.json.Json
import kotlin.random.Random

class UpdatesRepositoryImpl : UpdatesRepository {

    @Suppress("MaxLineLength")
    override suspend fun isLatestVersion(): Flow<Response> = flowOf(
        Response(
            code = 200,
            description = "OK",
            response = Json.encodeToString(Random.nextInt(1, 100) <= 95)
        )
    )
}