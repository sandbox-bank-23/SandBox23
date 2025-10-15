package com.example.myapplication.auth.ui.screens

import androidx.compose.foundation.layout.Arrangement
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
import com.example.myapplication.auth.ui.viewmodel.AuthorizationViewModel
import com.example.myapplication.core.ui.components.HeadingText
import com.example.myapplication.core.ui.components.InputTextField
import com.example.myapplication.core.ui.components.PrimaryButton
import com.example.myapplication.core.ui.components.SecondaryButton
import com.example.myapplication.core.ui.theme.Height60
import com.example.myapplication.core.ui.theme.Height80
import com.example.myapplication.core.ui.theme.PaddingBase
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthorizationScreen(
    modifier: Modifier = Modifier,
    authVm: AuthorizationViewModel = koinViewModel(),
    navController: NavController
) {
    // Состояния для текстовых полей из ViewModel
    val loginState = authVm.loginState.collectAsState().value
    val passwordState = authVm.passwordState.collectAsState().value

    // Состояние для флага перехода на экран создания пин-кода
    val pinCodeCreateTriggerState = authVm.pinCodeCreateTriggerState.collectAsState().value

    LaunchedEffect(pinCodeCreateTriggerState) {
        if (pinCodeCreateTriggerState) navController.navigate(route = AuthScreen.PinPadCreate.route)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(PaddingBase),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HeadingText(stringResource(R.string.authorization), false)
        Spacer(modifier = Modifier.Companion.height(Height80))
        InputTextField(
            state = loginState,
            onTextChange = { authVm.onLoginChange(it) }
        )
        Spacer(modifier = Modifier.Companion.height(PaddingBase))
        InputTextField(
            state = passwordState,
            onTextChange = { authVm.onPasswordChange(it) }
        )
        Spacer(modifier = Modifier.Companion.height(Height60))
        // Блок кнопок
        PrimaryButton(stringResource(R.string.enter)) { authVm.authorize() }
        Spacer(modifier = Modifier.Companion.height(PaddingBase))
        SecondaryButton(stringResource(R.string.registration)) {
            navController.navigate(route = AuthScreen.Registration.route)
        }
    }
}