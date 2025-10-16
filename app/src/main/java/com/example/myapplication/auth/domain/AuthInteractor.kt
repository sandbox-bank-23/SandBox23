package com.example.myapplication.auth.domain

import com.example.myapplication.auth.domain.model.AuthData
import com.example.myapplication.auth.domain.model.AuthRequest
import com.example.myapplication.auth.domain.state.Result

interface AuthInteractor {

    suspend fun login(authRequest: AuthRequest): Result<AuthData>

    suspend fun register(authRequest: AuthRequest): Result<AuthData>
}