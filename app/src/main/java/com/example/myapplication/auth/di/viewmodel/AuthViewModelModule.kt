package com.example.myapplication.auth.di.viewmodel

import com.example.myapplication.auth.ui.viewmodel.AuthorizationViewModel
import com.example.myapplication.auth.ui.viewmodel.PinPadViewModel
import com.example.myapplication.auth.ui.viewmodel.RegistrationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authViewModelModule = module {
    viewModel {
        AuthorizationViewModel(get(), get(), get())
    }

    viewModel {
        RegistrationViewModel(get(), get(), get())
    }

    viewModel {
        PinPadViewModel(get(), get())
    }
}