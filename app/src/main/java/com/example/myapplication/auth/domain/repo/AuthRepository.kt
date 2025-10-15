package com.example.myapplication.auth.domain.repo

import com.example.myapplication.auth.domain.model.AuthData
import com.example.myapplication.auth.domain.state.Result

/**
 * Интерфейс для авторизации и регистрации.
 *
 * Вся валидация должна проводиться до отправки запроса.
 */
interface AuthRepository {
    suspend fun login(email: String, password: String): Result<AuthData>

    suspend fun register(email: String, password: String): Result<AuthData>
}