package com.example.myapplication.profile.di

import com.example.myapplication.profile.ui.viewmodel.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val profileViewModelModule = module {
    viewModel {
        ProfileViewModel()
    }
}