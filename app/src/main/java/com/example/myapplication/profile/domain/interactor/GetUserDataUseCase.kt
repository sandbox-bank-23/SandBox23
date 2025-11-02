package com.example.myapplication.profile.domain.interactor

import com.example.myapplication.profile.domain.UserData

interface GetUserDataUseCase {
    suspend operator fun invoke(): UserData
}