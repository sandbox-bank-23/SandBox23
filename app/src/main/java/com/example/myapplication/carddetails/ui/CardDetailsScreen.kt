package com.example.myapplication.carddetails.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.carddetails.domain.models.CardDetailsState
import com.example.myapplication.core.domain.models.Card
import com.example.myapplication.core.ui.components.CardItem
import com.example.myapplication.core.ui.theme.AppTypography
import com.example.myapplication.core.domain.models.CardType
import com.example.myapplication.core.ui.components.BasicDialog
import com.example.myapplication.core.ui.components.PrimaryButton
import com.example.myapplication.core.ui.components.SimpleIconDialog
import com.example.myapplication.core.ui.state.Routes
import com.example.myapplication.core.ui.theme.ButtonMainHeight
import com.example.myapplication.core.ui.theme.Padding12dp
import com.example.myapplication.core.ui.theme.PaddingBase
import com.example.myapplication.core.ui.theme.backgroundLight
import com.example.myapplication.core.ui.theme.onTertiaryLight
import com.example.myapplication.core.ui.theme.tertiaryLight
import org.koin.androidx.compose.koinViewModel
import java.text.DecimalFormat


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDetailsScreen(
    navController: NavHostController,
    viewModel: CardDetailsViewModel = koinViewModel<CardDetailsViewModel>()
) {

    val card = Card(
        id = 4000_1234_3215_7893,
        cvv = 12,
        endDate = "07/2007",
        owner = "Ivanova Oksana",
        type = "Credit",
        percent = 2.5 ,
        balance = 500000
    )

    val cardLimit : Long = 500000
    val cardDebt : Long = 0

    val cardDetailsState = viewModel.cardDetailsState.collectAsState().value
    val offlineCardDialog = remember { mutableStateOf(false) }
    val successCardDialog = remember { mutableStateOf(false) }

    when (cardDetailsState) {
        CardDetailsState.Content -> { }
        CardDetailsState.Offline -> {
            offlineCardDialog.value = true
        }
        CardDetailsState.Online -> {
            offlineCardDialog.value = false
        }
        CardDetailsState.Success -> {
            successCardDialog.value = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                windowInsets = androidx.compose.foundation.layout.WindowInsets(0),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                ),
                title = {
                    Text(
                        text = stringResource(R.string.cards),
                        style = AppTypography.titleLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = PaddingBase)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .size(48.dp)
                            .padding(start = PaddingBase)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.arrow_back),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                })
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
                .padding(horizontal = 12.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            BasicDialog(
                visible = offlineCardDialog.value,
                onDismissRequest = { offlineCardDialog.value = false },
                onConfirmation = {
                    viewModel.closeCard(card)
                },
                dialogTitle = stringResource(R.string.offline),
                confirmButtonText = stringResource(R.string.try_again),
                dismissButtonText = stringResource(R.string.close),
            )

            SimpleIconDialog(
                visible = successCardDialog.value,
                onDismissRequest = { successCardDialog.value = false },
                dialogTitle = stringResource(R.string.saved_successfully),
                dismissButtonText = stringResource(R.string.close),
                icon = Icons.Default.Check
            )

            Column(
                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                CardItem(
                    cardHolderName = stringResource(R.string.card_holder_default),
                    cardBalance = card.balance
                ) { }
            }
            Column(
                modifier = Modifier.padding(top = Padding12dp)
            ) {
                CardInfoRow(stringResource(R.string.card_holder), card.owner)
                CardInfoRow(
                    stringResource(R.string.card_number),
                    creditCardNumberFormat(card.id)
                )
                CardInfoRow(stringResource(R.string.card_date), card.endDate)
                CardInfoRow(stringResource(R.string.card_cvv),
                    DecimalFormat("000").format(card.cvv))
                Spacer(modifier = Modifier.Companion.height(12.dp))
                CardInfoRow(stringResource(R.string.card_about))
                when (card.type) {
                    CardType.DEBIT -> {
                        CardInfoRow(stringResource(R.string.card_type_debit))
                        CardInfoRow(value = stringResource(R.string.card_debit_info_row1))
                        CardInfoRow(value = stringResource(R.string.card_debit_info_row2))
                        CardInfoRow(value = stringResource(R.string.card_debit_info_row3))
                    }
                    CardType.CREDIT -> {
                        CardInfoRow(stringResource(R.string.card_type_credit))
                        CardInfoRow(stringResource(R.string.card_credit_available),
                            stringResource(R.string.card_limit,
                                DecimalFormat("###,###")
                                    .format(card.balance), DecimalFormat("###,###")
                                    .format(cardLimit)))
                        CardInfoRow(stringResource(R.string.card_credit_debt),
                            stringResource(R.string.card_debt,
                                DecimalFormat("#,##0.00")
                                    .format(cardDebt)))
                        CardInfoRow(stringResource(R.string.card_credit_grace_period),
                            stringResource(R.string.card_credit_grace_value))
                    }
                }
                Spacer(modifier = Modifier.Companion.height(12.dp))
                PrimaryButton(stringResource(R.string.card_top_up)) {
                    navController.navigate(Routes.TRANSFERS.route)
                }
                Spacer(modifier = Modifier.Companion.height(12.dp))
                CloseButton(stringResource(R.string.card_close)) {
                    viewModel.closeCard(card)
                }
                Spacer(modifier = Modifier.Companion.height(12.dp))
            }
        }
    }
}

@Composable
private fun CardInfoRow(title: String? = null, value: String? = null) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        if (!title.isNullOrEmpty()) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start,
                text = title,
                style = AppTypography.bodyMedium.copy(
                    fontWeight = FontWeight.W600,
                )
            )
        }
        if (!value.isNullOrEmpty()) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start,
                text = value,
                style = AppTypography.bodyMedium.copy(
                    fontWeight = FontWeight.W400,
                )
            )
        }
    }
}

@Composable
fun CloseButton(label: String, isEnabled: Boolean = true, onClick: () -> Unit) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(ButtonMainHeight),
        onClick = onClick,
        enabled = isEnabled,
        border =  BorderStroke(
            color = onTertiaryLight,
            width = 1.dp
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = tertiaryLight
        ),
        content = {
            Text(
                text = label,
                maxLines = 1,
                style = AppTypography.titleMedium.copy(
                    color = onTertiaryLight
                )
            )
        }
    )
}

private fun creditCardNumberFormat(number: Long): String {
    val preparedString = number.toString()
    val result = StringBuilder()
    for (i in preparedString.indices) {
        if (i % 4 == 0 && i != 0) {
            result.append(" ")
        }
        result.append(preparedString[i])
    }
    return result.toString()
}