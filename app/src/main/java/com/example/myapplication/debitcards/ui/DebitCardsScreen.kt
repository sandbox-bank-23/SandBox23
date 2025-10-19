package com.example.myapplication.debitcards.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.core.ui.components.BasicDialog
import com.example.myapplication.core.ui.components.CardItem
import com.example.myapplication.core.ui.components.PrimaryButton
import com.example.myapplication.core.ui.theme.AppTypography
import com.example.myapplication.core.ui.theme.NavTextInactiveDark
import com.example.myapplication.core.ui.theme.Padding12dp
import com.example.myapplication.core.ui.theme.PaddingBase
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebitCardsScreen(
    //navController: NavHostController,
    viewModel: DebitCardsViewModel = koinViewModel<DebitCardsViewModel>()
) {
    val cardDetailsState = viewModel.debitCardsState.collectAsState().value
    val offlineCardDialog = remember { mutableStateOf(false) }
    val successCardDialog = remember { mutableStateOf(false) }

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
                        onClick = { // navController.popBackStack()
                             },
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
                }
            )
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

            /*
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
                onDismissRequest = { successCardDialog.value = false },
                dialogTitle = stringResource(R.string.saved_successfully),
                dismissButtonText = stringResource(R.string.close),
                icon = Icons.Default.Check
            )
            */
            Column(
                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                CardItem(
                    cardHolderName = stringResource(R.string.card_holder_default),
                    cardBalance = 100000000F
                ) { }
                Text(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 24.dp),
                    textAlign = TextAlign.Start,
                    text = stringResource(R.string.card_debit),
                    style = AppTypography.headlineSmall
                )
            }
            Column(
                modifier = Modifier.padding(top = Padding12dp)
            ) {
                CardInfoRowBox(
                    stringResource(R.string.card_debit_info_title1),
                    stringResource(R.string.card_debit_info_text1)
                )
                CardInfoRowBox(
                    stringResource(R.string.card_debit_info_title2),
                    stringResource(R.string.card_debit_info_text2)
                )
                CardInfoRowBox(
                    text = stringResource(R.string.card_debit_info_text3)
                )
                Spacer(modifier = Modifier.Companion.height(12.dp))
                PrimaryButton(stringResource(R.string.card_open)) {
                    //navController.navigate(Routes.TRANSFERS.route)
                }
                Spacer(modifier = Modifier.Companion.height(12.dp))
            }
        }
    }
}

@Composable
private fun CardInfoRowBox(title: String? = null, text: String) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, NavTextInactiveDark),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            if (!title.isNullOrEmpty()) {
                Text(
                    modifier = Modifier.padding(bottom = 4.dp),
                    text = title,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Start,
                    style = AppTypography.bodyMedium.copy(),
                    lineHeight = 24.sp
                )
            }
            Text(
                text = text,
                textAlign = TextAlign.Start,
                style = AppTypography.bodyLarge.copy(),
                lineHeight = 24.sp
            )
        }
    }
}

@Preview
@Composable
fun DebitCardsScreenPreview() {
    DebitCardsScreen()
}