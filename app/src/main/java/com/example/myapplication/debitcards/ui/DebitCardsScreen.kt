package com.example.myapplication.debitcards.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.core.ui.components.BasicDialog
import com.example.myapplication.core.ui.components.CardInfoBox
import com.example.myapplication.core.ui.components.CardItem
import com.example.myapplication.core.ui.components.PrimaryButton
import com.example.myapplication.core.ui.components.SimpleIconDialog
import com.example.myapplication.core.ui.components.SimpleTopBar
import com.example.myapplication.core.ui.theme.AppTypography
import com.example.myapplication.core.ui.theme.Padding12dp
import org.koin.androidx.compose.koinViewModel

const val CASHBACK = 30
const val CARD_BALANCE_DEF = 100_000_000L

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebitCardsScreen(
    navController: NavHostController,
    viewModel: DebitCardsViewModel = koinViewModel<DebitCardsViewModel>()
) {

    val cardDetailsState = viewModel.debitCardsState.collectAsState().value
    val offlineCardDialog = remember { mutableStateOf(false) }
    val successCardDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            SimpleTopBar(stringResource(R.string.cards)) { navController.popBackStack() }
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
        contentColor = MaterialTheme.colorScheme.onSurface
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
                    viewModel.openCard()
                },
                dialogTitle = stringResource(R.string.offline),
                confirmButtonText = stringResource(R.string.try_again),
                dismissButtonText = stringResource(R.string.close),
            )
            SimpleIconDialog(
                visible = successCardDialog.value,
                onDismissRequest = {
                    successCardDialog.value = false
                    navController.popBackStack()
                },
                dialogTitle = stringResource(R.string.saved_successfully),
                dismissButtonText = stringResource(R.string.close),
                icon = Icons.Default.Check
            )
            Column(
                modifier = Modifier.fillMaxWidth().padding(vertical = Padding12dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                CardItem(
                    cardHolderName = stringResource(R.string.card_holder_default),
                    cardBalance = CARD_BALANCE_DEF
                ) { }
            }
            Column {
                Text(
                    modifier = Modifier.padding(Padding12dp),
                    textAlign = TextAlign.Start,
                    text = stringResource(R.string.card_debit),
                    style = AppTypography.headlineSmall
                )
                CardInfoBox(
                    stringResource(R.string.card_debit_info_title1),
                    stringResource(R.string.card_debit_info_text1)
                )
                CardInfoBox(
                    stringResource(
                        R.string.card_debit_info_title2,
                        CASHBACK
                    ),
                    stringResource(R.string.card_debit_info_text2)
                )
                CardInfoBox(
                    text = stringResource(R.string.card_debit_info_text3)
                )
                Spacer(modifier = Modifier.Companion.height(Padding12dp))
                PrimaryButton(stringResource(R.string.card_open)) {
                    viewModel.openCard()
                }
                Spacer(modifier = Modifier.Companion.height(Padding12dp))
            }
        }
    }
}
