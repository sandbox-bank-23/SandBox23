package com.example.myapplication.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.myapplication.core.ui.theme.PaddingBase
import kotlin.random.Random

@Composable
fun Greeting(innerPadding: PaddingValues) {
    var digitsEntered by remember { mutableIntStateOf(0) }
    var isError by remember { mutableStateOf(false) }
    // Экран превью компонентов UI-Kit
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(PaddingBase),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Блок полей экрана авторизации
        HeadingText("Авторизация", false)
        Spacer(modifier = Modifier.Companion.height(PaddingBase))
        InputTextField(
            label = "Email",
            isPassword = false,
            isError = false,
            isSuccess = false
        )
        Spacer(modifier = Modifier.Companion.height(PaddingBase))
        InputTextField(
            label = "Пароль",
            isPassword = true,
            isError = false,
            isSuccess = false
        )
        Spacer(modifier = Modifier.Companion.height(PaddingBase))
        // Блок полей ввода пин-кода
        @Suppress("MagicNumber")
        PinCodeProgressBar(digitsEntered, 6, isError)
        Spacer(modifier = Modifier.Companion.height(PaddingBase))
        PinPad(
            isFingerprintEnabled = true,
            onDigitClick = {
                when (digitsEntered) {
                    in 0..4 -> digitsEntered++
                    5 -> {
                        isError = Random.nextBoolean()
                        digitsEntered++
                    }

                    6 -> {
                        digitsEntered = 0
                        isError = false
                    }
                }
            },
            onBackspaceClick = {
                when (digitsEntered) {
                    in 1..5 -> digitsEntered--
                    6 -> {
                        isError = false
                        digitsEntered = 0
                    }

                    else -> Unit
                }
            },
            onFingerprintClick = {
                isError = false
                digitsEntered = 0
            }
        )
        Spacer(modifier = Modifier.Companion.height(PaddingBase))
        // Блок кнопок
        PrimaryButton("Вход") { }
        Spacer(modifier = Modifier.Companion.height(PaddingBase))
        SecondaryButton("Регистрация") { }
    }
}