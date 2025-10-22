package com.example.myapplication.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.core.ui.theme.AppTypography
import com.example.myapplication.core.ui.theme.CardBackground
import com.example.myapplication.core.ui.theme.CardBigCircle
import com.example.myapplication.core.ui.theme.CardSmallCircle
import com.example.myapplication.core.ui.theme.CardTextDark
import com.example.myapplication.core.ui.theme.CornerRadiusMedium
import com.example.myapplication.core.ui.theme.Padding18dp
import com.example.myapplication.core.ui.theme.Padding24dp
import com.example.myapplication.core.ui.theme.PaddingQuarter
import com.example.myapplication.core.ui.theme.ZeroPadding
import java.text.DecimalFormat

const val CENTS_DIVIDE = 100



@Composable
fun CardItem(
    cardHolderName: String = "",
    cardBalance: Long? = null,
    cardType: String? = null,
    cardNumber: String? = null,
    isTemplate: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .height(205.dp)
            .width(328.dp)
            .padding(ZeroPadding)
            .background(
                color = CardBackground,
                shape = RoundedCornerShape(CornerRadiusMedium)
            )
            .clip(RoundedCornerShape(CornerRadiusMedium))
            .clickable { onClick?.invoke() }
    ) {
        Box(
            modifier = Modifier
                .requiredSize(350.dp)
                .offset(x = (-120).dp, y = 25.dp)
                .background(
                    color = CardBigCircle,
                    shape = CircleShape
                )
        )
        Box(
            modifier = Modifier
                .requiredSize(190.dp)
                .offset(x = 225.dp, y = (-60).dp)
                .padding(ZeroPadding)
                .background(
                    color = CardSmallCircle,
                    shape = CircleShape
                )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = Padding24dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            CardHolderName(cardHolderName, isTemplate)
            Column {
                CardData(cardType, cardNumber, isTemplate)
                CardBalance(cardBalance, isTemplate)
            }
        }
    }
}


@Composable
private fun CardHolderName(name: String = "", isTemplate: Boolean = false) {
    Text(
        modifier = Modifier.padding(horizontal = Padding24dp),
        color = CardTextDark,
        textAlign = TextAlign.Start,
        text = if (isTemplate) {
            stringResource(R.string.card_holder_default)
        } else { 
            name
        },
        maxLines = 1,
        style = AppTypography.titleLarge.copy(
            fontSize = 24.sp,
            fontWeight = FontWeight.W400
        )
    )
}


@Composable
private fun CardData(cardType: String?, cardNumber: String?, isTemplate: Boolean = false) {
    if (!isTemplate) {
        Column {
            if (!cardType.isNullOrEmpty()) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = Padding24dp, vertical = PaddingQuarter)
                        .height(16.dp),
                    color = CardTextDark,
                    textAlign = TextAlign.Start,
                    text = cardType,
                    maxLines = 1,
                    style = AppTypography.bodyMedium.copy(
                        fontWeight = FontWeight.W500
                    )
                )
            }
            if (!cardNumber.isNullOrEmpty()) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = Padding18dp, vertical = PaddingQuarter)
                        .height(24.dp),
                    color = CardTextDark,
                    textAlign = TextAlign.Start,
                    text = cardNumber,
                    maxLines = 1,
                    style = AppTypography.bodyLarge.copy(
                        fontWeight = FontWeight.W500
                    )
                )
            }
        }
    }
}

@Suppress("MagicNumber")
@Composable
private fun CardBalance(balance: Long? = null, isTemplate: Boolean = false) {
    var balanceFormat = ""
    if (isTemplate) balanceFormat = stringResource(R.string.card_balance_default)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Padding24dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (balance != null && !isTemplate) {
            balanceFormat = DecimalFormat("#,##0.00 \u20BD")
                .format(balance / CENTS_DIVIDE)
        }
        Text(
            modifier = Modifier.height(28.dp),
            color = CardTextDark,
            textAlign = TextAlign.Start,
            text = balanceFormat,
            maxLines = 1,
            style = AppTypography.titleLarge.copy(
                fontWeight = FontWeight.W500
            )
        )
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.height(20.dp),
        ) {
            Icon(
                painterResource(R.drawable.ic_card_mir_logo1),
                stringResource(R.string.card_logo_content_descr),
                tint = CardTextDark
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
                        stringResource(R.string.card_logo_content_descr),
                        tint = CardTextDark
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
                        stringResource(R.string.card_logo_content_descr),
                        tint = CardTextDark
                    )
                }
            }
        }
    }
}
