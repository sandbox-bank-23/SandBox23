package com.example.myapplication.profile.domain.interactor.impl

import com.example.myapplication.profile.domain.api.ProfileRepository
import com.example.myapplication.profile.domain.interactor.ClearAppDataUseCase

class ClearAppDataUseCaseImpl(
    private val profileRepository: ProfileRepository,
) : ClearAppDataUseCase {
    override suspend operator fun invoke() {
        profileRepository.clearAppData()
    }
}