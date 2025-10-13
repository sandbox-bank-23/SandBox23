package com.example.myapplication.auth.domain.api

import com.example.myapplication.core.data.network.Response

/**
 * Интерфейс для авторизации и регистрации.
 *
 * Вся валидация должна проводиться до отправки запроса.
 */
interface AuthRepository {
    suspend fun login(email: String, password: String): Response

    suspend fun register(email: String, password: String): Response
}