package com.example.myapplication.auth.domain.impl

import android.app.Application
import android.util.Patterns
import com.example.myapplication.R
import com.example.myapplication.auth.domain.AuthInteractor
import com.example.myapplication.auth.domain.model.AuthData
import com.example.myapplication.auth.domain.model.AuthRequest
import com.example.myapplication.auth.domain.repo.AuthRepository
import com.example.myapplication.core.data.model.Result

class AuthInteractorImpl(
    val authRepository: AuthRepository,
    val application: Application
) : AuthInteractor {
    override suspend fun login(authRequest: AuthRequest): Result<AuthData> {
        return authRepository.login(authRequest.email, authRequest.password)
    }

    override suspend fun register(authRequest: AuthRequest): Result<AuthData> {
        return authRepository.register(authRequest.email, authRequest.password)
    }

    override fun isEmailValid(email: String): Pair<Boolean, String> {
        return when {
            email.isEmpty() -> Pair(
                false,
                application.applicationContext.getString(R.string.login_empty_error)
            )
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> Pair(
                false,
                application.applicationContext.getString(R.string.login_is_not_email_error)
            )
            else -> Pair(true, "")
        }
    }

    override fun isPasswordValid(password: String): Pair<Boolean, String> {
        return when {
            password.contains(' ') -> Pair(
                false,
                application.applicationContext.getString(R.string.spaces_in_password_error)
            )
            password.length < PASSWORD_MIN_LENGTH -> Pair(
                false,
                application.applicationContext.getString(R.string.short_password_error)
            )
            else -> Pair(true, "")
        }
    }

    companion object {
        const val PASSWORD_MIN_LENGTH = 6
    }
}