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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.core.domain.models.Card
import com.example.myapplication.core.ui.components.CardItem
import com.example.myapplication.core.ui.theme.AppTypography
import com.example.myapplication.core.domain.models.CardType
import com.example.myapplication.core.ui.components.PrimaryButton
import com.example.myapplication.core.ui.theme.ButtonMainHeight
import com.example.myapplication.core.ui.theme.PaddingBase
import com.example.myapplication.core.ui.theme.backgroundLight
import com.example.myapplication.core.ui.theme.tertiaryLight


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun CardsDetailsScreen() {

    val card = Card(
        id = 4000_1234_3215_7893,
        cvv = 12,
        endDate = "07/2007",
        owner = "Ivanova Oksana",
        type = "Debit",
        percent = 2.5 ,
        balance = 500000
    )
    Scaffold(
        topBar = {
            TopAppBar(
                windowInsets = androidx.compose.foundation.layout.WindowInsets(0),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundLight,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
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
                        onClick = { },
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
                .padding(innerPadding)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                CardItem(
                    cardHolderName = stringResource(R.string.card_holder_default),
                    cardBalance = card.balance
                ) { }
            }
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                CardInfoRow(stringResource(R.string.card_holder), card.owner)
                CardInfoRow(
                    stringResource(R.string.card_number),
                    creditCardNumberFormat(card.id)
                )
                CardInfoRow(stringResource(R.string.card_date), card.endDate)
                CardInfoRow(stringResource(R.string.card_cvv), card.cvv.toString())
                Spacer(modifier = Modifier.Companion.height(12.dp))
                CardInfoRow(stringResource(R.string.card_about))
                when (card.type) {
                    CardType.DEBIT -> {
                        CardInfoRow(stringResource(R.string.card_type_debit))
                        CardInfoRow(value = stringResource(R.string.card_debit_info))
                    }

                    CardType.CREDIT -> {
                        CardInfoRow(stringResource(R.string.card_type_credit))
                    }
                }
            }
            Column {
                Spacer(modifier = Modifier.Companion.height(12.dp))
                PrimaryButton(stringResource(R.string.card_top_up)) {}
                Spacer(modifier = Modifier.Companion.height(12.dp))
                CloseButton(stringResource(R.string.card_close)) {}
                Spacer(modifier = Modifier.Companion.height(12.dp))
            }
        }
    }
}


@Composable
private fun CardInfoRow(title: String? = null, value: String? = null) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        if (!title.isNullOrEmpty()) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start,
                text = title,
                style = AppTypography.bodyMedium.copy(
                    fontWeight = FontWeight.W500,
                    lineHeight = 32.sp
                )
            )
        }
        if (!value.isNullOrEmpty()) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start,
                text = value,
                style = AppTypography.bodyMedium.copy(
                    lineHeight = 32.sp
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
            color = tertiaryLight,
            width = 1.dp
        ),
        content = {
            Text(
                text = label,
                maxLines = 1,
                style = AppTypography.titleMedium.copy(
                    color = tertiaryLight
                )
            )
        }
    )
}

fun creditCardNumberFormat(number: Long): String {
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



