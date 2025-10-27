package com.example.myapplication.auth.ui.utils

import android.util.Patterns

object ValidationUtils {

    const val PASSWORD_MIN_LENGTH = 6

    fun isEmailValid(email: String): Pair<Boolean, Boolean> {
        return when {
            email.isEmpty() -> Pair(
                true,
                false
            )

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> Pair(
                false,
                true
            )

            else -> Pair(false, false)
        }
    }

    fun isPasswordValid(password: String): Pair<Boolean, Boolean> {
        return when {
            password.contains(' ') -> Pair(
                true,
                false
            )

            password.length < PASSWORD_MIN_LENGTH -> Pair(
                false,
                true
            )

            else -> Pair(false, false)
        }
    }
}