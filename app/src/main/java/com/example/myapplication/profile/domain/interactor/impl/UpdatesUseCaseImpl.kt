package com.example.myapplication.profile.domain.interactor.impl

import com.example.myapplication.profile.domain.api.UpdatesRepository
import com.example.myapplication.profile.domain.interactor.UpdatesUseCase

class UpdatesUseCaseImpl(private val updatesRepository: UpdatesRepository) : UpdatesUseCase {
    override suspend fun isLatestVersion() = updatesRepository.isLatestVersion()
}