package com.example.myapplication.auth.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.auth.navigation.AuthScreen
import com.example.myapplication.auth.ui.state.AuthScreenState
import com.example.myapplication.auth.ui.viewmodel.AuthorizationViewModel
import com.example.myapplication.core.ui.components.HeadingText
import com.example.myapplication.core.ui.components.InputTextField
import com.example.myapplication.core.ui.components.PrimaryButton
import com.example.myapplication.core.ui.components.SecondaryButton
import com.example.myapplication.core.ui.model.TextInputState
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
    val screenState = authVm.screenState.collectAsState().value
    val email = authVm.email.collectAsState().value
    val password = authVm.password.collectAsState().value

    AuthorizationContent(
        modifier = modifier,
        screenState = screenState,
        email = email,
        password = password,
        onEmailChange = authVm::onLoginChange,
        onPasswordChange = authVm::onPasswordChange,
        onLoginClick = { authVm.authorize() },
        onRegistrationClick = { navController.navigate(AuthScreen.Registration.route) },
        onSuccess = { navController.navigate(AuthScreen.PinPadCreate.route) }
    )
}

@Composable
private fun AuthorizationContent(
    modifier: Modifier,
    screenState: AuthScreenState,
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRegistrationClick: () -> Unit,
    onSuccess: () -> Unit
) {
    if (screenState is AuthScreenState.Successful) {
        onSuccess()
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(PaddingBase),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HeadingText(stringResource(R.string.authorization), false)
        Spacer(modifier = Modifier.height(Height80))

        when (screenState) {
            AuthScreenState.Default ->
                AuthTextFields(email, password, onEmailChange, onPasswordChange)

            is AuthScreenState.ErrorState ->
                AuthErrorFields(screenState, email, password, onEmailChange, onPasswordChange)

            else -> Unit
        }

        Spacer(modifier = Modifier.height(Height60))
        PrimaryButton(stringResource(R.string.enter)) { onLoginClick() }
        Spacer(modifier = Modifier.height(PaddingBase))
        SecondaryButton(stringResource(R.string.registration)) { onRegistrationClick() }
    }
}

@Composable
private fun AuthTextFields(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit
) {
    InputTextField(
        state = TextInputState(label = stringResource(R.string.email), valueText = email),
        onTextChange = onEmailChange
    )
    Spacer(modifier = Modifier.height(PaddingBase))
    InputTextField(
        state = TextInputState(
            label = stringResource(R.string.password),
            isPassword = true,
            valueText = password
        ),
        onTextChange = onPasswordChange
    )
}

@Composable
private fun AuthErrorFields(
    errorState: AuthScreenState.ErrorState,
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit
) {
    InputTextField(
        state = TextInputState(
            label = stringResource(R.string.email),
            valueText = email,
            isError = errorState.emailLengthError || errorState.emailConsistError,
            supportingText = if (errorState.emailLengthError || errorState.emailConsistError) {
                stringResource(R.string.login_is_not_email_error)
            } else {
                ""
            }
        ),
        onTextChange = onEmailChange
    )
    Spacer(modifier = Modifier.height(PaddingBase))
    InputTextField(
        state = TextInputState(
            label = stringResource(R.string.password),
            valueText = password,
            isError = errorState.passLengthError || errorState.passEmptyError,
            isPassword = true,
            supportingText = if (errorState.passLengthError || errorState.passEmptyError) {
                stringResource(R.string.pass_constraint)
            } else {
                ""
            }
        ),
        onTextChange = onPasswordChange
    )
}