package com.example.myapplication.creditcards.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.core.ui.components.BasicDialog
import com.example.myapplication.core.ui.components.CardInfoBox
import com.example.myapplication.core.ui.components.CardItem
import com.example.myapplication.core.ui.components.PrimaryButton
import com.example.myapplication.core.ui.components.SimpleIconDialog
import com.example.myapplication.core.ui.components.SimpleTopBar
import com.example.myapplication.core.ui.theme.AppTypography
import com.example.myapplication.core.ui.theme.CornerRadiusLarge
import com.example.myapplication.core.ui.theme.Padding12dp
import com.example.myapplication.core.ui.theme.Padding8dp
import com.example.myapplication.core.ui.theme.PaddingBase
import com.example.myapplication.creditcards.domain.models.CreditCardsState
import org.koin.androidx.compose.koinViewModel
import java.text.DecimalFormat

const val SERVICE_COST = 990L
const val CREDIT_LIMIT_MAX = 1_000_000L
const val CASHBACK = 30

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreditCardsScreen(
    navController: NavHostController,
    viewModel: CreditCardsViewModel = koinViewModel<CreditCardsViewModel>()
) {
    val creditCardsState = viewModel.creditCardsState.collectAsState().value
    val offlineCardDialog = remember { mutableStateOf(false) }
    val successCardDialog = remember { mutableStateOf(false) }

    // Сходить в репу за этим
    val serviceCost = SERVICE_COST
    val creditLimitMax = CREDIT_LIMIT_MAX
    val cashback = CASHBACK

    when (creditCardsState) {
        is CreditCardsState.Offline -> {
            offlineCardDialog.value = true
        }
        is CreditCardsState.Online -> {
            offlineCardDialog.value = false
        }
        is CreditCardsState.Success -> {
            successCardDialog.value = true
        }
        is CreditCardsState.Loading -> return
        else -> {}
    }

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
                .padding(horizontal = Padding12dp)
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
                dialogTitle = stringResource(R.string.card_open_success),
                dismissButtonText = stringResource(R.string.close),
                icon = ImageVector.vectorResource(id = R.drawable.ic_card_open_ok)
            )

            Column(
                modifier = Modifier.fillMaxWidth().padding(vertical = Padding12dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                CardItem(isTemplate = true)
            }
            Column {
                Text(
                    modifier = Modifier.padding(Padding12dp),
                    textAlign = TextAlign.Start,
                    text = stringResource(R.string.card_credit),
                    style = AppTypography.headlineSmall
                )
                CardInfoBox(
                    stringResource(R.string.card_credit_info_title1),
                    stringResource(
                        R.string.card_credit_info_text1,
                        serviceCost
                    )
                )
                CardInfoBox(
                    stringResource(
                        R.string.card_credit_info_title2,
                        creditLimitMax
                    ),
                    stringResource(R.string.card_credit_info_text2)
                )
                CardInfoBox(
                    stringResource(R.string.card_credit_info_title3),
                    stringResource(R.string.card_credit_info_text3)
                )
                CardInfoBox(
                    stringResource(R.string.card_credit_info_title4),
                    stringResource(
                        R.string.card_credit_info_text4,
                        cashback
                    )
                )
                CreditLimitSlider(max = creditLimitMax, viewModel = viewModel)
                Spacer(modifier = Modifier.Companion.height(Padding12dp))
                if (creditCardsState is CreditCardsState.Error) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.card_count_max),
                        textAlign = TextAlign.Center,
                        style = AppTypography.labelLarge.copy(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W500
                        ),
                        lineHeight = 24.sp,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                    Spacer(modifier = Modifier.Companion.height(Padding12dp))
                    PrimaryButton(stringResource(R.string.card_open), isEnabled = false) {}
                } else {
                    PrimaryButton(stringResource(R.string.card_open)) {
                        viewModel.openCard()
                    }
                }
                Spacer(modifier = Modifier.Companion.height(Padding12dp))
            }
        }
    }
}

@Composable
fun CreditLimitSlider(min: Long = 0L, max: Long = 1_000_000L, viewModel: CreditCardsViewModel) {
    var creditLimitValue by remember { mutableFloatStateOf(((max - min) / 2).toFloat()) }
    var sliderWidth by remember { mutableFloatStateOf(0f) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(vertical = Padding8dp, horizontal = Padding12dp),
            textAlign = TextAlign.Start,
            text = stringResource(R.string.card_credit_limit),
            style = AppTypography.bodyMedium.copy(
                fontWeight = FontWeight.W600
            )
        )

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(
                        x = calculateTextOffset(
                            creditLimitValue,
                            min.toFloat(),
                            max.toFloat(),
                            sliderWidth
                        )
                    )
                    .background(
                        color = MaterialTheme.colorScheme.inverseSurface,
                        shape = RoundedCornerShape(CornerRadiusLarge)
                    )
                    .padding(vertical = Padding12dp, horizontal = PaddingBase)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDrag = { change, dragAmount ->
                                change.consume()
                                val valueChange = (max - min) * dragAmount.x / sliderWidth
                                val newValue = creditLimitValue + valueChange
                                creditLimitValue = newValue.coerceIn(min.toFloat(), max.toFloat())
                            },
                            onDragEnd = {
                                viewModel.creditLimitValue = creditLimitValue.toLong()
                            }
                        )
                    },
                text = DecimalFormat(
                    stringResource(R.string.balance_pattern)
                ).format(creditLimitValue),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.surface,
                style = AppTypography.labelLarge.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W500
                ),
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Padding8dp)
                .onGloballyPositioned { layoutCoordinates ->
                    sliderWidth = layoutCoordinates.size.width.toFloat()
                }
        ) {
            Slider(
                modifier = Modifier.fillMaxWidth(),
                value = creditLimitValue,
                onValueChange = { creditLimitValue = it },
                onValueChangeFinished = {
                    viewModel.creditLimitValue = creditLimitValue.toLong()
                },
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.secondary,
                    activeTrackColor = MaterialTheme.colorScheme.primary,
                    inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
                steps = 0,
                valueRange = min.toFloat()..max.toFloat()
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = Padding8dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start,
                text = DecimalFormat(
                    stringResource(R.string.balance_pattern)
                ).format(min),
                style = AppTypography.bodyMedium.copy(
                    fontWeight = FontWeight.W500
                )
            )
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End,
                text = DecimalFormat(
                    stringResource(R.string.balance_pattern)
                ).format(max),
                style = AppTypography.bodyMedium.copy(
                    fontWeight = FontWeight.W500
                )
            )
        }
    }
}

@Suppress("MagicNumber")
@Composable
private fun calculateTextOffset(
    currentValue: Float,
    minValue: Float,
    maxValue: Float,
    sliderWidth: Float
): Dp {
    if (sliderWidth == 0f) return 0.dp
    val progress = (currentValue - minValue) / (maxValue - minValue)
    val offsetFraction = (progress - 0.5f) * 0.9f
    return with(LocalDensity.current) {
        (offsetFraction * sliderWidth).toDp()
    }
}