package com.example.myapplication.auth.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.example.myapplication.auth.ui.state.RegScreenState
import com.example.myapplication.auth.ui.viewmodel.RegistrationViewModel
import com.example.myapplication.core.ui.components.HeadingText
import com.example.myapplication.core.ui.components.InputTextField
import com.example.myapplication.core.ui.components.PrimaryButton
import com.example.myapplication.core.ui.components.SimpleTopBar
import com.example.myapplication.core.ui.model.TextInputState
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
    val screenState = registerVm.screenState.collectAsState().value

    Box(modifier = modifier) {
        SimpleTopBar(
            title = stringResource(R.string.authorization),
            onBackClick = { navController.navigateUp() }
        )

        RegistrationContent(
            modifier = modifier
                .fillMaxSize()
                .padding(PaddingBase),
            screenState = screenState,
            login = registerVm.loginState.collectAsState().value,
            pass = registerVm.passwordState.collectAsState().value,
            pass2 = registerVm.password2State.collectAsState().value,
            onLoginChange = registerVm::onLoginChange,
            onPassChange = registerVm::onPasswordChange,
            onPass2Change = registerVm::onPassword2Change,
            onRegisterClick = registerVm::register,
            onSuccess = { navController.navigate(AuthScreen.PinPadCreate.route) }
        )
    }
}

@Composable
private fun RegistrationContent(
    modifier: Modifier,
    screenState: RegScreenState,
    login: String,
    pass: String,
    pass2: String,
    onLoginChange: (String) -> Unit,
    onPassChange: (String) -> Unit,
    onPass2Change: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onSuccess: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HeadingText(stringResource(R.string.registration), false)
        Spacer(modifier = Modifier.height(Height80))

        when (screenState) {
            is RegScreenState.Default -> {
                RegistrationDefaultFields(
                    onLoginChange,
                    onPassChange,
                    onPass2Change
                )
            }

            is RegScreenState.ErrorState -> {
                RegistrationErrorFields(
                    state = screenState,
                    login = login,
                    pass = pass,
                    pass2 = pass2,
                    onLoginChange = onLoginChange,
                    onPassChange = onPassChange,
                    onPass2Change = onPass2Change
                )
            }

            is RegScreenState.Successful -> onSuccess()
        }

        Spacer(modifier = Modifier.height(Height60))
        PrimaryButton(
            stringResource(R.string.register),
            onClick = onRegisterClick
        )
    }
}

@Composable
private fun RegistrationDefaultFields(
    onLoginChange: (String) -> Unit,
    onPassChange: (String) -> Unit,
    onPass2Change: (String) -> Unit
) {
    InputTextField(
        state = TextInputState(label = stringResource(R.string.email)),
        onTextChange = onLoginChange
    )
    Spacer(modifier = Modifier.height(PaddingBase))
    InputTextField(
        state = TextInputState(label = stringResource(R.string.password), isPassword = true),
        onTextChange = onPassChange
    )
    Spacer(modifier = Modifier.height(PaddingBase))
    InputTextField(
        state = TextInputState(label = stringResource(R.string.repeat_password), isPassword = true),
        onTextChange = onPass2Change
    )
}

@Composable
private fun RegistrationErrorFields(
    state: RegScreenState.ErrorState,
    login: String,
    pass: String,
    pass2: String,
    onLoginChange: (String) -> Unit,
    onPassChange: (String) -> Unit,
    onPass2Change: (String) -> Unit
) {
    InputTextField(
        state = TextInputState(
            label = stringResource(R.string.email),
            valueText = login,
            isError = state.emailLengthError || state.emailConsistError,
            supportingText = if (state.emailLengthError || state.emailConsistError) {
                stringResource(R.string.login_is_not_email_error)
            } else {
                ""
            }
        ),
        onTextChange = onLoginChange
    )
    Spacer(modifier = Modifier.height(PaddingBase))
    InputTextField(
        state = TextInputState(
            label = stringResource(R.string.password),
            valueText = pass,
            isError = state.passLengthError || state.passEmptyError,
            supportingText = if (state.passLengthError || state.passEmptyError) {
                stringResource(R.string.pass_constraint)
            } else {
                ""
            },
            isPassword = true
        ),
        onTextChange = onPassChange
    )
    Spacer(modifier = Modifier.height(PaddingBase))
    InputTextField(
        state = TextInputState(
            label = stringResource(R.string.repeat_password),
            valueText = pass2,
            isError = state.passDiffError,
            supportingText = if (state.passDiffError) {
                stringResource(R.string.passwords_are_different)
            } else {
                ""
            },
            isPassword = true
        ),
        onTextChange = onPass2Change
    )
}