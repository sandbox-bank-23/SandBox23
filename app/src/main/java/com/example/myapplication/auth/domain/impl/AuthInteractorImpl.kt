package com.example.myapplication.auth.domain.impl

import android.util.Patterns
import com.example.myapplication.R
import com.example.myapplication.auth.domain.AuthInteractor
import com.example.myapplication.auth.domain.model.AuthData
import com.example.myapplication.auth.domain.model.AuthRequest
import com.example.myapplication.auth.domain.repo.AuthRepository
import com.example.myapplication.auth.domain.state.Result

class AuthInteractorImpl(
    val authRepository: AuthRepository
) : AuthInteractor {
    override suspend fun login(authRequest: AuthRequest): Result<AuthData> {
        return authRepository.login(authRequest.email, authRequest.password)
    }

    override suspend fun register(authRequest: AuthRequest): Result<AuthData> {
        return authRepository.register(authRequest.email, authRequest.password)
    }

    companion object {
        const val PASSWORD_MIN_LENGTH = 6
    }
}