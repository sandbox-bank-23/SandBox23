package com.example.myapplication.loans.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.core.ui.components.BasicDialog
import com.example.myapplication.core.ui.components.CardInfoBox
import com.example.myapplication.core.ui.components.ExceededCreditDialog
import com.example.myapplication.core.ui.components.FlagSlider
import com.example.myapplication.core.ui.components.PrimaryButton
import com.example.myapplication.core.ui.components.SimpleIconDialog
import com.example.myapplication.core.ui.components.SimpleTopBar
import com.example.myapplication.core.ui.components.SliderBox
import com.example.myapplication.core.ui.theme.Height56
import com.example.myapplication.core.ui.theme.Padding16dp
import com.example.myapplication.loans.ui.state.LoansState
import com.example.myapplication.loans.ui.viewmodel.LoansViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun CreateLoanScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: LoansViewModel = koinViewModel<LoansViewModel>(),
) {

    val loansState by viewModel.stateLoans.collectAsState()
    Log.d("loansState", "$loansState")
    val initialCharacteristics = viewModel.initialCharacteristics.collectAsState().value

    var limitValue by remember { mutableIntStateOf(0) }
    var monthValue by remember { mutableIntStateOf(0) }

    val dialogExceeded = remember { mutableStateOf(value = false) }
    val dialogNetwork = remember { mutableStateOf(value = false) }
    val dialogApproved = remember { mutableStateOf(value = false) }

    when (loansState) {
        is LoansState.LimitExceeded -> {
            dialogExceeded.value = true
        }

        is LoansState.NetworkError -> {
            dialogNetwork.value = true
        }

        is LoansState.LoanApproved -> {
            dialogApproved.value = true
        }

        else -> {}
    }

    Scaffold(
        topBar = {
            SimpleTopBar(stringResource(R.string.credit_registration)) { navController.popBackStack() }
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            BasicDialog(
                visible = dialogNetwork.value,
                onDismissRequest = { dialogNetwork.value = false },
                onConfirmation = {
                    viewModel.openLoan(
                        limit = limitValue,
                        month = monthValue
                    )
                },
                dialogTitle = stringResource(R.string.offline),
                confirmButtonText = stringResource(R.string.try_again),
                dismissButtonText = stringResource(R.string.close),
            )
            SimpleIconDialog(
                visible = dialogApproved.value,
                onDismissRequest = {
                    dialogApproved.value = false
                    navController.popBackStack()
                },
                dialogTitle = stringResource(R.string.credit_open_success),
                dismissButtonText = stringResource(R.string.close),
                icon = ImageVector.vectorResource(id = R.drawable.ic_credit_open_ok)
            )
            ExceededCreditDialog(
                visible = dialogExceeded.value,
                onDismissRequest = {
                    dialogExceeded.value = false
                    navController.popBackStack()
                },
                dialogTitle = stringResource(R.string.exceeded_the_limit),
                dismissButtonText = stringResource(R.string.close),
                icon = ImageVector.vectorResource(id = R.drawable.ic_credit_exceeded),
                dialogText = stringResource(R.string.credit_count_max)
            )
            Box(
                modifier = modifier
                    .padding(Padding16dp)
            ) {
                Column() {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(Height56)
                            .background(MaterialTheme.colorScheme.surfaceContainerHigh),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,

                        ) {
                        Text(
                            modifier = modifier
                                .padding(start = Padding16dp),
                            text = stringResource(R.string.select_conditiopns)
                        )
                    }

                    Spacer(modifier = modifier.height(Padding16dp))

                    CardInfoBox(
                        title = stringResource(R.string.percent_rate),
                        text = stringResource(R.string.percent_rate_before_25)
                    )

                    Spacer(modifier = modifier.height(Padding16dp))

                    SliderBox(
                        trackSlider = initialCharacteristics.moneyLimit,
                        flagSlider = FlagSlider.LIMIT_CREDIT,
                        dataSlider = { value -> limitValue = value }
                    )

                    Spacer(modifier = modifier.height(Padding16dp))

                    SliderBox(
                        trackSlider = initialCharacteristics.monthLimit,
                        flagSlider = FlagSlider.PERIOD_CREDIT,
                        dataSlider = { value -> monthValue = value }
                    )

                    Spacer(modifier = modifier.height(Padding16dp))

                    CardInfoBox(
                        title = stringResource(R.string.monthly_payment),
                        text = stringResource(R.string.in_rubles)
                    )

                    Spacer(modifier = modifier.height(Padding16dp))

                    PrimaryButton(
                        label = stringResource(R.string.open_loan),
                        isEnabled = true,
                    ) {
                        viewModel.openLoan(
                            limit = limitValue,
                            month = monthValue
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CheckLoansScreen() {
    val navController = rememberNavController()
    CreateLoanScreen(
        modifier = Modifier,
        navController = navController
    )
}
