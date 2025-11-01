package com.example.myapplication.profile.domain.interactor

import kotlinx.coroutines.flow.Flow

interface ThemeInteractor {
    suspend fun changeTheme()

    fun getTheme(): Flow<Boolean>
}