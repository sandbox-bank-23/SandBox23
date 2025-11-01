package com.example.myapplication.deposits.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.core.ui.components.BasicDialog
import com.example.myapplication.core.ui.components.CardInfoBox
import com.example.myapplication.core.ui.components.PrimaryButton
import com.example.myapplication.core.ui.components.SimpleIconDialog
import com.example.myapplication.core.ui.components.slider.CustomSlider
import com.example.myapplication.core.ui.components.slider.FlagSlider
import com.example.myapplication.core.ui.theme.AppTypography
import com.example.myapplication.core.ui.theme.CornerRadiusMedium
import com.example.myapplication.core.ui.theme.Padding12dp
import com.example.myapplication.core.ui.theme.Padding16dp
import com.example.myapplication.core.ui.theme.Padding18dp
import com.example.myapplication.core.ui.theme.Padding23dp
import com.example.myapplication.core.ui.theme.Padding24dp
import com.example.myapplication.core.ui.theme.Padding2dp
import com.example.myapplication.core.ui.theme.Padding32dp
import com.example.myapplication.core.ui.theme.Padding48dp
import com.example.myapplication.core.ui.theme.Padding8dp
import com.example.myapplication.core.ui.theme.PaddingBase
import com.example.myapplication.core.ui.theme.surfaceContainerLight
import com.example.myapplication.deposits.ui.state.NewDepositScreenState
import com.example.myapplication.deposits.ui.viewmodel.NewDepositViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewDepositScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: NewDepositViewModel = koinViewModel()
) {
    var value by remember { mutableIntStateOf(0) }
    val initialCharacteristics = viewModel.initialCharacteristics.collectAsState().value
    val dialogNetwork = remember { mutableStateOf(false) }
    val dialogApproved = remember { mutableStateOf(false) }
    val newDepositScreenState by viewModel.newDepositScreenState.collectAsState()

    // Реакция на состояние экрана
    LaunchedEffect(newDepositScreenState) {
        when (newDepositScreenState) {
            is NewDepositScreenState.Content -> {
                dialogNetwork.value = false
                dialogApproved.value = true
            }

            is NewDepositScreenState.Empty, is NewDepositScreenState.Error -> {
                dialogNetwork.value = true
                dialogApproved.value = false
            }

            is NewDepositScreenState.Loading -> {
                dialogNetwork.value = false
                dialogApproved.value = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                windowInsets = WindowInsets(0),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                ),
                title = {
                    Text(
                        text = stringResource(R.string.deposit_opening),
                        style = AppTypography.titleLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = modifier.padding(start = PaddingBase)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = modifier
                            .size(Padding48dp)
                            .padding(start = PaddingBase)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.arrow_back),
                            modifier = modifier.size(Padding24dp)
                        )
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(Padding16dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicDialog(
                visible = dialogNetwork.value,
                onDismissRequest = {
                    dialogNetwork.value = false
                    navController.popBackStack()
                },
                onConfirmation = {
                    dialogNetwork.value = false
                    viewModel.openDep(value.toLong())
                },
                dialogTitle = stringResource(R.string.offline),
                confirmButtonText = stringResource(R.string.try_again),
                dismissButtonText = stringResource(R.string.close)
            )

            SimpleIconDialog(
                visible = dialogApproved.value,
                onDismissRequest = {
                    dialogApproved.value = false
                    navController.popBackStack()
                },
                dialogTitle = stringResource(R.string.deposit_open_success),
                dismissButtonText = stringResource(R.string.close),
                icon = ImageVector.vectorResource(id = R.drawable.ic_credit_open_ok)
            )

            Spacer(Modifier.height(Padding23dp))

            Card(
                shape = RoundedCornerShape(CornerRadiusMedium),
                border = BorderStroke(Padding2dp, MaterialTheme.colorScheme.primary),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .padding(vertical = Padding32dp, horizontal = PaddingBase),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.before_20_percent),
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.displaySmall,
                        maxLines = 1
                    )
                }
            }

            Spacer(Modifier.height(Padding18dp))

            CardInfoBox(
                stringResource(R.string.card_debit_info_title1),
                stringResource(R.string.free_price)
            )

            Spacer(Modifier.height(Padding18dp))

            CustomSlider(FlagSlider.PERIOD_DEPOSIT, initialCharacteristics) { sliderValue ->
                value = sliderValue
            }

            Spacer(Modifier.height(Padding18dp))

            // Начисление процентов
            Card(
                shape = RoundedCornerShape(Padding18dp),
                colors = CardDefaults.cardColors(containerColor = surfaceContainerLight),
            ) {
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(Padding12dp, Padding8dp),
                    text = stringResource(R.string.percent_up),
                    color = MaterialTheme.colorScheme.scrim,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = true,
                        onClick = {},
                        enabled = true
                    )
                    Text(
                        stringResource(R.string.everyday),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.scrim
                    )
                }
            }

            Spacer(Modifier.height(PaddingBase))

            Card(
                shape = RoundedCornerShape(Padding18dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.padding(Padding12dp),
                    text = stringResource(R.string.deposit_period_percent),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(Modifier.height(Padding32dp))

            PrimaryButton(
                stringResource(R.string.open_deposit),
                true
            ) {
                viewModel.openDep(value.toLong())
            }
        }
    }
}
