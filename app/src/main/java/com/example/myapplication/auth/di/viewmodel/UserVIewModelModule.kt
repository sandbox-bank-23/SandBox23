package com.example.myapplication.auth.di.viewmodel

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import com.example.myapplication.auth.ui.viewmodel.UserViewModel

val userViewModelModule = module {
    viewModel {
        UserViewModel()
    }
}