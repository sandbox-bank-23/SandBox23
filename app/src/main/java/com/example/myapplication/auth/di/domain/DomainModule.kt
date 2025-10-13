package com.example.myapplication.auth.di.domain

import com.example.myapplication.auth.data.AuthRepositoryImpl
import com.example.myapplication.auth.domain.AuthInteractor
import com.example.myapplication.auth.domain.impl.AuthInteractorImpl
import com.example.myapplication.auth.domain.repo.AuthRepository
import com.example.myapplication.auth.ui.viewmodel.UserViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val domainModule = module {

    single<AuthRepository>{
        AuthRepositoryImpl(get())
    }

    single<AuthInteractor>{
        AuthInteractorImpl(get())
    }
}