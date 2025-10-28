@file:Suppress("MagicNumber")

package com.example.myapplication.profile.data.repo

import com.example.myapplication.core.data.network.Response
import com.example.myapplication.profile.domain.api.UpdatesRepository
import kotlinx.serialization.json.Json
import kotlin.random.Random
import kotlin.random.nextInt

class UpdatesRepositoryImpl : UpdatesRepository {

    @Suppress("MaxLineLength")
    override fun isLatestVersion() = when (Random.nextInt(1..1000)) {
        in 1..900 -> Response(
            code = 200,
            description = "OK",
            response = Json.encodeToString(true)
        )

        in 901..998 -> Response(
            code = 200,
            description = "OK",
            response = Json.encodeToString(false)
        )

        in 999..1000 -> Response(
            code = 420,
            description = "no",
            response = Json.encodeToString(NullPointerException())
        )

        else -> Response(
            code = 0,
            description = "",
            response = Json.encodeToString("")
        )

    }
}