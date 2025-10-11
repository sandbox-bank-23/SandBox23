package com.example.myapplication.cards.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import java.text.DecimalFormat


@Preview(showSystemUi = true)
@Composable
fun CardsScreen(
    //viewModel: CardsViewModel = koinViewModel<CardsViewModel>()
) {
    Column(
        modifier = Modifier.fillMaxSize(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(0.45f)
                .padding(bottom = 48.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            CardItem()
        }
        Column(
            modifier = Modifier.height(188.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            CreateCard("Оформить дебетовую карту", false) {}
            CreateCard("Оформить кредитную карту", true) {}
        }
    }
}

@Composable
private fun CreateCard(text: String, isCredit: Boolean, onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp)),
        containerColor = Color(0XFFF3EDF7),
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
                color = Color(0XFF65558F),
                textAlign = TextAlign.Start,
                text = text,
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium
            )
        },
    )
}


@Composable
fun CardItem(
    cardHolderName: String = "SandboxBank",
    cardName: String? = null,
    cardNumber: String? = null
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
                CardData(cardName, cardNumber)
                CardBalance(1000000f)
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
        style = MaterialTheme.typography.headlineMedium
    )
}


@Composable
private fun CardData(cardName: String?, cardNumber: String?) {
    Column() {
        if (!cardName.isNullOrEmpty())
        Text(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 4.dp)
                .height(16.dp),
            color = Color(0XFF222222),
            textAlign = TextAlign.Start,
            text = cardName,
            maxLines = 1,
            style = MaterialTheme.typography.bodySmall
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
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun CardBalance(balance: Float) {
    val balanceFormat = DecimalFormat("#,##0 \u20BD").format(balance)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.height(28.dp),
            color = Color(0XFF222222),
            textAlign = TextAlign.Start,
            text = balanceFormat,
            maxLines = 1,
            style = MaterialTheme.typography.titleLarge
        )
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.height(20.dp),
        ){
            Icon(
                painterResource(R.drawable.ic_card_mir_logo1),
                "Extended floating action button."
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
                        "Extended floating action button."
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
                        "Extended floating action button."
                    )
                }
            }
        }
    }
}
