package com.example.myapplication.cards.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.myapplication.R
import com.example.myapplication.cards.domain.models.CardsState
import com.example.myapplication.core.ui.components.CardItem
import com.example.myapplication.core.ui.theme.AppTypography
import com.example.myapplication.core.ui.theme.CardDialogButtonHeight
import com.example.myapplication.core.ui.theme.CardIconHeight
import com.example.myapplication.core.ui.theme.CornerRadiusMedium
import com.example.myapplication.core.ui.theme.Padding24dp
import com.example.myapplication.core.ui.theme.PaddingBase
import com.example.myapplication.core.ui.theme.onPrimaryLight
import com.example.myapplication.core.ui.theme.onSurfaceLight
import com.example.myapplication.core.ui.theme.primaryLight
import com.example.myapplication.core.ui.theme.surfaceContainerLight
import com.example.myapplication.core.ui.theme.surfaceContainerLowestLight

const val CARD_BALANCE_DEF = 1000000f

@Preview(showSystemUi = true)
@Composable
fun CardsScreen(
    //viewModel: CardsViewModel = koinViewModel<CardsViewModel>()
) {
    //val cardState = viewModel.cardState.collectAsState().value
    var cardState by remember { mutableStateOf<CardsState>(CardsState.Empty) }

    val cardHolderName = stringResource(R.string.card_holder_default)
    var cardBalance: Float?
    var cardType: String? = null
    var cardNumber: String? = null

    val openCardDialog = remember { mutableStateOf(false) }

    when (cardState) {
        is CardsState.Empty -> {
            cardType = stringResource(R.string.card_type_default)
            cardNumber = stringResource(R.string.card_number_default)
            cardBalance = null
        }
        is CardsState.Cards -> {
            cardBalance = CARD_BALANCE_DEF
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        CardDialog(
            visible = openCardDialog.value,
            onDismissRequest = { openCardDialog.value = false },
            onConfirmation = {
                openCardDialog.value = false
                cardState = CardsState.Cards
            },
            dialogTitle = stringResource(R.string.card_dialog_title),
            confirmButtonText = stringResource(R.string.card_dialog_confirm),
            dismissButtonText = stringResource(R.string.card_dialog_dismiss),
        )
        Column(
            modifier = Modifier.fillMaxHeight(0.5f),
            verticalArrangement = Arrangement.Bottom
        ) {
            CardItem(
                cardHolderName = cardHolderName,
                cardBalance = cardBalance,
                cardType = cardType,
                cardNumber = cardNumber
            ) {
                if (cardState is CardsState.Empty)
                    openCardDialog.value = true
            }
        }
        Spacer(modifier = Modifier.Companion.height(48.dp))
        Column(
            modifier = Modifier.height(188.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            CreateCardButton(
                stringResource(R.string.card_create_debit),
                false
            ) {}
            CreateCardButton(
                stringResource(R.string.card_create_credit),
                true
            ) {}
        }
    }
}

@Composable
private fun CreateCardButton(text: String, isCredit: Boolean, onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = PaddingBase)
            .clip(RoundedCornerShape(CornerRadiusMedium)),
        containerColor = surfaceContainerLight,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp,
            hoveredElevation = 2.dp,
            focusedElevation = 1.dp,
        ),
        onClick = { onClick.invoke() },
        icon = {
            if (isCredit) {
                Image(
                    painterResource(R.drawable.ic_card_credit),
                    stringResource(R.string.card_create_credit),
                    modifier = Modifier.size(CardIconHeight)
                )
            } else {
                Image(
                    painterResource(R.drawable.ic_card_debit),
                    stringResource(R.string.card_create_debit),
                    modifier = Modifier.size(CardIconHeight)
                )
            }
        },
        text = {
            Text(
                modifier = Modifier,
                color = primaryLight,
                textAlign = TextAlign.Start,
                text = text,
                maxLines = 1,
                style = AppTypography.bodyMedium
            )
        },
    )
}


@Composable
fun CardDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    confirmButtonText: String,
    dismissButtonText: String,
) {
    if (visible) {
        Dialog(
            onDismissRequest = {
                onDismissRequest()
            }
        ) {
            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = surfaceContainerLowestLight
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Padding24dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(bottom = Padding24dp),
                        color = onSurfaceLight,
                        textAlign = TextAlign.Center,
                        text = dialogTitle,
                        style = AppTypography.headlineSmall.copy(
                            fontWeight = FontWeight.W400
                        )
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        TextButton(
                            modifier = Modifier
                                .height(CardDialogButtonHeight)
                                .padding(horizontal = 2.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = onPrimaryLight,
                                contentColor = MaterialTheme.colorScheme.tertiaryContainer
                            ),
                            onClick = {
                                onDismissRequest()
                            },
                        ) {
                            Text(
                                textAlign = TextAlign.Center,
                                text = dismissButtonText,
                                style = AppTypography.labelLarge.copy(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.W500
                                )
                            )
                        }
                        Button(
                            modifier = Modifier
                                .height(CardDialogButtonHeight)
                                .padding(horizontal = 2.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                contentColor = onPrimaryLight
                            ),
                            onClick = {
                                onConfirmation()
                            }
                        ) {
                            Text(
                                textAlign = TextAlign.Center,
                                text = confirmButtonText,
                                style = AppTypography.labelLarge.copy(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.W500
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}