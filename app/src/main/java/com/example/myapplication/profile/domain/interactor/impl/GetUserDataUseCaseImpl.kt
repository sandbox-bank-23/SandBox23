package com.example.myapplication.profile.domain.interactor.impl

import com.example.myapplication.profile.domain.UserData
import com.example.myapplication.profile.domain.api.ProfileRepository
import com.example.myapplication.profile.domain.interactor.GetUserDataUseCase

class GetUserDataUseCaseImpl(
    private val profileRepository: ProfileRepository,
) : GetUserDataUseCase {
    override suspend fun invoke(): UserData {
        return profileRepository.getUserData()
    }
}