package com.example.myapplication.auth.di.viewmodel

import com.example.myapplication.auth.ui.viewmodel.UserViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val userViewModelModule = module {
    viewModel {
        UserViewModel()
    }
}