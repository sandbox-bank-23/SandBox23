package com.example.myapplication.profile.di

import com.example.myapplication.profile.data.repo.ProfileRepositoryImpl
import com.example.myapplication.profile.domain.api.ProfileRepository
import com.example.myapplication.profile.domain.interactor.ClearAppDataUseCase
import com.example.myapplication.profile.domain.interactor.GetUserDataUseCase
import com.example.myapplication.profile.domain.interactor.ThemeInteractor
import com.example.myapplication.profile.domain.interactor.UpdatesUseCase
import com.example.myapplication.profile.domain.interactor.impl.ClearAppDataUseCaseImpl
import com.example.myapplication.profile.domain.interactor.impl.GetUserDataUseCaseImpl
import com.example.myapplication.profile.domain.interactor.impl.ThemeInteractorImpl
import com.example.myapplication.profile.domain.interactor.impl.UpdatesUseCaseImpl
import com.example.myapplication.profile.ui.viewmodel.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {
    single<ProfileRepository> { ProfileRepositoryImpl(get(), get(), get(), get(), get()) }
    factory<UpdatesUseCase> { UpdatesUseCaseImpl(get<ProfileRepository>()) }
    single<ThemeInteractor> { ThemeInteractorImpl(get()) }
    single<ClearAppDataUseCase> { ClearAppDataUseCaseImpl(get()) }
    single<GetUserDataUseCase> { GetUserDataUseCaseImpl(get()) }

    viewModel {
        ProfileViewModel(
            get(),
            get(),
            get(),
            get()
        )
    }
}