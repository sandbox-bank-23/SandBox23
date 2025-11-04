package com.example.myapplication.profile.domain.interactor.impl

import com.example.myapplication.profile.domain.api.ProfileRepository
import com.example.myapplication.profile.domain.interactor.UpdatesUseCase

class UpdatesUseCaseImpl(private val profileRepository: ProfileRepository) : UpdatesUseCase {
    override suspend fun isLatestVersion() = profileRepository.isLatestVersion()
}