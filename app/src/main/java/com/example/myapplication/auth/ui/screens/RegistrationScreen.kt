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
    // Состояния для экрана из ViewModel
    val screenState = registerVm.screenState.collectAsState().value

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
            when (screenState) {
                is RegScreenState.Default -> {
                    InputTextField(
                        state = TextInputState(
                            label = stringResource(R.string.email)
                        ),
                        onTextChange = { registerVm.onLoginChange(it) }
                    )
                    Spacer(modifier = Modifier.Companion.height(PaddingBase))
                    InputTextField(
                        state = TextInputState(
                            label = stringResource(R.string.password),
                            isPassword = true
                        ),
                        onTextChange = { registerVm.onPasswordChange(it) }
                    )
                    Spacer(modifier = Modifier.Companion.height(PaddingBase))
                    InputTextField(
                        state = TextInputState(
                            label = stringResource(R.string.repeat_password),
                            isPassword = true
                        ),
                        onTextChange = { registerVm.onPassword2Change(it) }
                    )
                }

                is RegScreenState.ErrorState -> {
                    InputTextField(
                        state = TextInputState(
                            label = stringResource(R.string.email),
                            valueText = registerVm.loginState.collectAsState().value,
                            isError = (!screenState.emailLengthError || !screenState.emailConsistError),
                            supportingText = if (!screenState.emailLengthError || !screenState.emailConsistError) {
                                stringResource(R.string.login_is_not_email_error)
                            } else {
                                ""
                            }
                        ),
                        onTextChange = { registerVm.onLoginChange(it) }
                    )
                    Spacer(modifier = Modifier.Companion.height(PaddingBase))
                    InputTextField(
                        state = TextInputState(
                            label = stringResource(R.string.password),
                            valueText = registerVm.passwordState.collectAsState().value,
                            isError = !screenState.passLengthError || !screenState.passEmptyError,
                            supportingText = if (!screenState.passLengthError || !screenState.passEmptyError) {
                                stringResource(R.string.pass_constraint)
                            } else {
                                ""
                            },
                            isPassword = true
                        ),
                        onTextChange = { registerVm.onPasswordChange(it) }
                    )
                    Spacer(modifier = Modifier.Companion.height(PaddingBase))
                    InputTextField(
                        state = TextInputState(
                            label = stringResource(R.string.repeat_password),
                            valueText = registerVm.password2State.collectAsState().value,
                            isError = !screenState.passDiffError,
                            supportingText = if (!screenState.passDiffError) {
                                stringResource(R.string.passwords_are_different)
                            } else {
                                ""
                            },
                            isPassword = true
                        ),
                        onTextChange = { registerVm.onPassword2Change(it) }
                    )
                }

                is RegScreenState.Successful -> {
                    navController.navigate(route = AuthScreen.PinPadCreate.route)
                }
            }
            Spacer(modifier = Modifier.Companion.height(Height60))
            // Блок кнопок
            PrimaryButton(stringResource(R.string.register)) {
                registerVm.register()
            }
        }
        SimpleTopBar(
            title = stringResource(R.string.authorization),
            onBackClick = {
                navController.navigateUp()
            }
        )
    }
}