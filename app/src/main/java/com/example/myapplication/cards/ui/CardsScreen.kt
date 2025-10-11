package com.example.myapplication.cards.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.myapplication.R
import com.example.myapplication.core.ui.theme.primaryLight
import com.example.myapplication.core.ui.theme.surfaceContainerLight
import com.example.myapplication.core.ui.theme.surfaceContainerLowestLight
import java.text.DecimalFormat


@Preview(showSystemUi = true)
@Composable
fun CardsScreen(paddingValues: PaddingValues = PaddingValues(0.dp)
    //viewModel: CardsViewModel = koinViewModel<CardsViewModel>()
) {

    val isCardListEmpty = true

    val cardHolderName = "SandboxBank"
    var cardBalance: Float? = 1000000f
    var cardType: String? = null
    var cardNumber: String? = null

    val openCardDialog = remember { mutableStateOf(false) }

    if (isCardListEmpty) {
        cardType = "Amazon Platinum"
        cardNumber = "0000 ···· ···· 0000"
        cardBalance = null
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
                },
                dialogTitle = "Нет созданных виртуальных карт",
                confirmButtonText = "Оформить",
                dismissButtonText = "В другой раз",
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight(0.45f)
                    .padding(bottom = 48.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                CardItem(
                    cardHolderName = cardHolderName,
                    cardBalance = cardBalance,
                    cardType = cardType,
                    cardNumber = cardNumber
                ) {
                    if (isCardListEmpty)
                        openCardDialog.value = true
                }
            }
            Column(
                modifier = Modifier.height(188.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                CreateCardButton("Оформить дебетовую карту", false) {}
                CreateCardButton("Оформить кредитную карту", true) {}
            }
        }
}

@Composable
private fun CreateCardButton(text: String, isCredit: Boolean, onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp)),
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
                    "Extended floating action button.",
                    modifier = Modifier.size(40.dp)
                )
            } else {
                Image(
                    painterResource(R.drawable.ic_card_debit),
                    "Extended floating action button.",
                    modifier = Modifier.size(40.dp)
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
                style = MaterialTheme.typography.bodyMedium
            )
        },
    )
}


@Composable
fun CardItem(
    cardHolderName: String,
    cardBalance: Float?,
    cardType: String? = null,
    cardNumber: String? = null,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(205.dp)
            .width(328.dp)
            .padding(0.dp)
            .background(
                color = Color(0XFFE0D9FF),
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick.invoke() }
    ) {
        Box(
            modifier = Modifier
                .requiredSize(350.dp)
                .offset(x = (-120).dp, y = 25.dp)
                .background(
                    color = Color(0xFFD2F1E4),
                    shape = CircleShape
                )
        )
        Box(
            modifier = Modifier
                .requiredSize(190.dp)
                .offset(x = 225.dp, y = (-60).dp)
                .padding(0.dp)
                .background(
                    color = Color(0xFFFFD7B6),
                    shape = CircleShape
                )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            CardHolderName(cardHolderName)
            Column {
                CardData(cardType, cardNumber)
                CardBalance(cardBalance)
            }
        }
    }
}


@Composable
private fun CardHolderName(name: String) {
    Text(
        modifier = Modifier.padding(horizontal = 24.dp),
        color = Color(0XFF222222),
        textAlign = TextAlign.Start,
        text = name,
        maxLines = 1,
        style = MaterialTheme.typography.titleLarge.copy(
            fontSize = 24.sp,
            fontWeight = FontWeight.W400
        )
    )
}


@Composable
private fun CardData(cardType: String?, cardNumber: String?) {
    Column() {
        if (!cardType.isNullOrEmpty())
            Text(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 4.dp)
                    .height(16.dp),
                color = Color(0XFF222222),
                textAlign = TextAlign.Start,
                text = cardType,
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.W500
                )
            )
        if (!cardNumber.isNullOrEmpty())
            Text(
                modifier = Modifier
                    .padding(horizontal = 18.dp, vertical = 4.dp)
                    .height(24.dp),
                color = Color(0XFF222222),
                textAlign = TextAlign.Start,
                text = cardNumber,
                maxLines = 1,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.W500
                )
            )
    }
}

@Composable
private fun CardBalance(balance: Float?) {
    var balanceFormat: String = ""
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (balance != null)
            balanceFormat = DecimalFormat("#,##0 \u20BD").format(balance)
        Text(
            modifier = Modifier.height(28.dp),
            color = Color(0XFF222222),
            textAlign = TextAlign.Start,
            text = balanceFormat,
            maxLines = 1,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.W500
            )
        )
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.height(20.dp),
        ){
            Icon(
                painterResource(R.drawable.ic_card_mir_logo1),
                "Extended floating action button.",
                tint = Color.Black
            )
            Column(
                modifier = Modifier.padding(start = 1.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.height(9.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        painterResource(R.drawable.ic_card_mir_logo2),
                        "Extended floating action button.",
                        tint = Color.Black
                    )
                }
                Row(
                    modifier = Modifier
                        .height(11.dp)
                        .padding(0.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        painterResource(R.drawable.ic_card_mir_logo3),
                        "Extended floating action button.",
                        tint = Color.Black
                    )
                }
            }
        }
    }
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
                        .padding(vertical = 24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(bottom = 24.dp),
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        text = dialogTitle,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.W400
                        )
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        TextButton(
                            modifier = Modifier.height(40.dp),
                            onClick = {
                                onDismissRequest()
                            },
                        ) {
                            Text(
                                textAlign = TextAlign.Center,
                                text = dismissButtonText,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                        Button(
                            modifier = Modifier.height(40.dp),
                            onClick = {
                                onConfirmation()
                            }
                        ) {
                            Text(
                                textAlign = TextAlign.Center,
                                text = confirmButtonText,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }
            }
        }
    }
}