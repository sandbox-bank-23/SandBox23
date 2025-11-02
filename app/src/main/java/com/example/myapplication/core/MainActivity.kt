package com.example.myapplication.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.myapplication.core.ui.compose.App
import com.example.myapplication.core.ui.theme.SandBox23Theme
import com.example.myapplication.profile.ui.viewmodel.ProfileViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val profileViewModel: ProfileViewModel = koinViewModel()

            val isDarkTheme by profileViewModel
                .themeInteractor
                .getTheme()
                .collectAsState(initial = false)

            SandBox23Theme(darkTheme = isDarkTheme) {
                App()
            }
        }
    }
}
