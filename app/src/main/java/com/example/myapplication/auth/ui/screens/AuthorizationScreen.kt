package com.example.myapplication.auth.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.myapplication.R
import com.example.myapplication.auth.ui.viewmodel.UserViewModel
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
    userVm: UserViewModel = koinViewModel(),
    onRegistrationClick: () -> Unit = {}
) {
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
            label = stringResource(R.string.email),
            isPassword = false,
            isError = false,
            isSuccess = false
        )
        Spacer(modifier = Modifier.Companion.height(PaddingBase))
        InputTextField(
            label = stringResource(R.string.password),
            isPassword = true,
            isError = false,
            isSuccess = false
        )
        Spacer(modifier = Modifier.Companion.height(Height60))
        // Блок кнопок
        PrimaryButton(stringResource(R.string.enter)) { userVm.authorize() }
        Spacer(modifier = Modifier.Companion.height(PaddingBase))
        SecondaryButton(stringResource(R.string.registration)) { onRegistrationClick() }
    }
}