package com.example.myapplication.auth.ui.screens

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.myapplication.auth.navigation.AuthScreen
import com.example.myapplication.auth.ui.state.PinPadMode
import com.example.myapplication.auth.ui.state.PinPadState
import com.example.myapplication.auth.ui.viewmodel.PinPadViewModel
import com.example.myapplication.core.ui.components.HeadingText
import com.example.myapplication.core.ui.components.PinCodeProgressBar
import com.example.myapplication.core.ui.components.PinPad
import com.example.myapplication.core.ui.theme.Padding28dp
import com.example.myapplication.core.ui.theme.Padding48dp
import com.example.myapplication.core.ui.theme.PaddingBase
import org.koin.androidx.compose.koinViewModel

@Composable
fun PinPadScreen(
    modifier: Modifier = Modifier,
    pinPadVm: PinPadViewModel = koinViewModel(),
    navController: NavController,
    pinPadMode: PinPadMode
) {
    val context = LocalContext.current

    // Состояние для пин-пада
    val pinPadState = pinPadVm.pinPadState.collectAsState().value

    // Состояние для навигации
    val navState = pinPadVm.navState.collectAsState().value

    // Состояние для Toast
    val errorMessage = pinPadVm.errorMessageState.collectAsState().value

    LaunchedEffect(pinPadMode) {
        pinPadVm.updatePinPadMode(pinPadMode)
    }

    LaunchedEffect(navState) {
        if (navState.isNotEmpty()) {
            val routeDelete = when (pinPadMode) {
                PinPadMode.Create -> AuthScreen.PinPadCreate.route
                PinPadMode.Validate -> AuthScreen.PinPadValidate.route
            }
            navController.navigate(route = navState) {
                popUpTo(routeDelete) {
                    inclusive = true
                }
            }
            pinPadVm.cleanNav()
        }
    }

    LaunchedEffect(errorMessage) {
        if (errorMessage.isNotEmpty()) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            pinPadVm.cleanErrorMessage()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(PaddingBase),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (pinPadState) {
            is PinPadState.Loading -> Unit
            is PinPadState.PinPad -> {
                HeadingText(pinPadState.title, true)
                Spacer(modifier = Modifier.height(Padding28dp))
                PinCodeProgressBar(pinPadState.digitsEntered, pinPadState.digitsTotal, pinPadState.isError)
                Spacer(modifier = Modifier.height(Padding48dp))
                PinPad(
                    isFingerprintEnabled = pinPadState.isFingerprintEnabled,
                    onDigitClick = { pinPadVm.onDigitClick(it) },
                    onBackspaceClick = { pinPadVm.onBackspaceClick() },
                    onFingerprintClick = { pinPadVm.onFingerprintClick() }
                )
            }
        }
    }
}