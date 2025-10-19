package com.example.myapplication.creditcards.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.myapplication.core.ui.components.BasicDialog
import com.example.myapplication.core.ui.components.CardItem
import com.example.myapplication.core.ui.components.PrimaryButton
import com.example.myapplication.core.ui.theme.AppTypography
import com.example.myapplication.core.ui.theme.CornerRadiusMedium
import com.example.myapplication.core.ui.theme.NavTextInactiveDark
import com.example.myapplication.core.ui.theme.Padding12dp
import com.example.myapplication.core.ui.theme.PaddingBase
import org.koin.androidx.compose.koinViewModel
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreditCardsScreen(
    //navController: NavHostController,
    //viewModel: CreditCardsViewModel = koinViewModel<CreditCardsViewModel>()
) {
    /*
    val cardDetailsState = viewModel.debitCardsState.collectAsState().value
    val offlineCardDialog = remember { mutableStateOf(false) }
    val successCardDialog = remember { mutableStateOf(false) }
    */
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
                    text = stringResource(R.string.card_credit),
                    style = AppTypography.headlineSmall
                )
            }
            Column(
                modifier = Modifier.padding(top = Padding12dp)
            ) {
                CardInfoRowBox(
                    stringResource(R.string.card_credit_info_title1),
                    stringResource(R.string.card_credit_info_text1)
                )
                CardInfoRowBox(
                    stringResource(R.string.card_credit_info_title2),
                    stringResource(R.string.card_credit_info_text2)
                )
                CardInfoRowBox(
                    stringResource(R.string.card_credit_info_title3),
                    stringResource(R.string.card_credit_info_text3)
                )
                CardInfoRowBox(
                    stringResource(R.string.card_credit_info_title4),
                    stringResource(R.string.card_credit_info_text4)
                )
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    textAlign = TextAlign.Start,
                    text = stringResource(R.string.card_credit_limit),
                    style = AppTypography.bodyMedium.copy(
                        fontWeight = FontWeight.W600
                    )
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
                    text = title,
                    textAlign = TextAlign.Start,
                    style = AppTypography.bodyMedium.copy(
                        fontWeight = FontWeight.W600
                    ),
                    lineHeight = 24.sp
                )
            }
            Text(
                text = text,
                textAlign = TextAlign.Start,
                style = AppTypography.bodySmall,
                lineHeight = 24.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SliderAdvancedExample() {
    var sliderPosition by remember { mutableFloatStateOf(500000f) }
    Column {
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.secondary,
                activeTrackColor = MaterialTheme.colorScheme.secondary,
                inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            steps = 9,
            valueRange = 0f..   1000000f
        )
        Text(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.onSurface,
                    shape = RoundedCornerShape(CornerRadiusMedium)
                )
                .padding(vertical = 8.dp, horizontal = 16.dp),
            text = DecimalFormat("#,##0 \u20BD").format(sliderPosition),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.surface,
            style = AppTypography.labelLarge.copy(
                fontSize = 14.sp
            ),
        )
    }
}


@Preview
@Composable
fun DebitCardsScreenPreview() {
    CreditCardsScreen()
}