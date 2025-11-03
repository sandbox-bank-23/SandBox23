package com.example.myapplication.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.myapplication.profile.ui.viewmodel.ProfileViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun rememberAppDarkTheme(viewModel: ProfileViewModel = koinViewModel()): Boolean {
    val isDarkThemeFlow = viewModel.themeInteractor.getTheme()
    val isDarkTheme by isDarkThemeFlow.collectAsState(initial = false)
    return isDarkTheme
}