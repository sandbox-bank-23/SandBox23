package com.example.myapplication.auth.ui.utils

import android.util.Patterns

object ValidationUtils {

    const val PASSWORD_MIN_LENGTH = 6

    fun isEmailValid(email: String): Pair<Boolean, Boolean> {
        return when {
            email.isEmpty() -> Pair(
                false,
                true
            )

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> Pair(
                true,
                false
            )

            else -> Pair(true, true)
        }
    }

    fun isPasswordValid(password: String): Pair<Boolean, Boolean> {
        return when {
            password.contains(' ') -> Pair(
                false,
                true
            )

            password.length < PASSWORD_MIN_LENGTH -> Pair(
                true,
                false
            )

            else -> Pair(true, true)
        }
    }
}