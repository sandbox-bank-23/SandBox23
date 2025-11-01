package com.example.myapplication.profile.domain.interactor.impl

import com.example.myapplication.profile.domain.api.ProfileRepository
import com.example.myapplication.profile.domain.interactor.ThemeInteractor
import kotlinx.coroutines.flow.Flow

class ThemeInteractorImpl(
    val profileRepository: ProfileRepository
) : ThemeInteractor {
    override suspend fun changeTheme() {
        profileRepository.changeTheme()
    }

    override fun getTheme(): Flow<Boolean> {
        return profileRepository.getTheme()
    }
}