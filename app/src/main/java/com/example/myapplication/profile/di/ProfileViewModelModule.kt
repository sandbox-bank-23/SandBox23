package com.example.myapplication.profile.di

import com.example.myapplication.profile.data.repo.UpdatesRepositoryImpl
import com.example.myapplication.profile.domain.api.UpdatesRepository
import com.example.myapplication.profile.domain.interactor.UpdatesUseCase
import com.example.myapplication.profile.domain.interactor.impl.UpdatesUseCaseImpl
import com.example.myapplication.profile.ui.viewmodel.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val profileViewModelModule = module {
    single<UpdatesRepository> { UpdatesRepositoryImpl() }
    factory<UpdatesUseCase> { UpdatesUseCaseImpl(get<UpdatesRepository>()) }

    viewModel {
        ProfileViewModel(
            updatesUseCase = get<UpdatesUseCase>()
        )
    }
}