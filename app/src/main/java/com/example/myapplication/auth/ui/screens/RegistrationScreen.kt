package com.example.myapplication.auth.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.auth.navigation.AuthScreen
import com.example.myapplication.auth.ui.viewmodel.RegistrationViewModel
import com.example.myapplication.core.ui.components.HeadingText
import com.example.myapplication.core.ui.components.InputTextField
import com.example.myapplication.core.ui.components.PrimaryButton
import com.example.myapplication.core.ui.components.SimpleTopBar
import com.example.myapplication.core.ui.theme.Height60
import com.example.myapplication.core.ui.theme.Height80
import com.example.myapplication.core.ui.theme.PaddingBase
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    registerVm: RegistrationViewModel = koinViewModel(),
    navController: NavController
) {
    // Состояния для текстовых полей из ViewModel
    val loginState = registerVm.loginState.collectAsState().value
    val passwordState = registerVm.passwordState.collectAsState().value
    val password2State = registerVm.password2State.collectAsState().value

    // Состояние для флага перехода на экран создания пин-кода
    val pinCodeCreateTriggerState = registerVm.pinCodeCreateTriggerState.collectAsState().value

    LaunchedEffect(pinCodeCreateTriggerState) {
        if (pinCodeCreateTriggerState) navController.navigate(route = AuthScreen.PinPadCreate.route)
    }

    Box(modifier = modifier) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(PaddingBase),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HeadingText(stringResource(R.string.registration), false)
            Spacer(modifier = Modifier.Companion.height(Height80))
            InputTextField(
                state = loginState,
                onTextChange = { registerVm.onLoginChange(it) }
            )
            Spacer(modifier = Modifier.Companion.height(PaddingBase))
            InputTextField(
                state = passwordState,
                onTextChange = { registerVm.onPasswordChange(it) }
            )
            Spacer(modifier = Modifier.Companion.height(PaddingBase))
            InputTextField(
                state = password2State,
                onTextChange = { registerVm.onPassword2Change(it) }
            )
            Spacer(modifier = Modifier.Companion.height(Height60))
            // Блок кнопок
            PrimaryButton(stringResource(R.string.register)) { registerVm.register() }
        }
        SimpleTopBar(
            title = stringResource(R.string.authorization),
            onBackClick = {
                navController.navigateUp()
            }
        )
    }
}