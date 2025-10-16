package com.example.myapplication.auth.domain

import com.example.myapplication.auth.domain.model.AuthData
import com.example.myapplication.auth.domain.model.AuthRequest
import com.example.myapplication.core.utils.Result

interface AuthInteractor {

    suspend fun login(authRequest: AuthRequest): Result<AuthData>

    suspend fun register(authRequest: AuthRequest): Result<AuthData>

    fun isEmailValid(email: String): Pair<Boolean, String>

    fun isPasswordValid(password: String): Pair<Boolean, String>

}