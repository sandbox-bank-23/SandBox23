package com.example.myapplication.carddetails.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.core.domain.models.CardType
import com.example.myapplication.core.ui.components.CardItem
import com.example.myapplication.core.ui.theme.AppTypography


@Preview(showSystemUi = true)
@Composable
fun CardsDetailsScreen() {

    val cardHolderName = "Ivanova Oksana"
    val cardBalance = 50000.00f
    val cardType = "Debit"

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {

        Column(
            modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            CardItem(
                cardHolderName = stringResource(R.string.card_holder_default),
                cardBalance = cardBalance) {
            }

        }

        CardInfoRow("Держатель карты",cardHolderName)
        CardInfoRow("Номер карты","4756 0000 0000 9018")
        CardInfoRow("Карта действует до","07/2027")
        CardInfoRow("CVC/CVV","001")

        CardInfoRow("О карте")
        CardInfoRow("Вид карты: ")

        CardInfoRow("Вид карты: ")

    }
}


@Composable
private fun CardInfoRow(title: String, value: String? = null) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(0.5f),
            textAlign = TextAlign.Start,
            text = title,
            maxLines = 1,
            style = AppTypography.bodyMedium.copy(
                fontWeight = FontWeight.W500
            )
        )
        if (!value.isNullOrEmpty()) {
            Text(
                modifier = Modifier.fillMaxWidth(0.5f),
                textAlign = TextAlign.Start,
                text = value,
                maxLines = 1,
                style = AppTypography.bodyMedium
            )
        }
    }
}


