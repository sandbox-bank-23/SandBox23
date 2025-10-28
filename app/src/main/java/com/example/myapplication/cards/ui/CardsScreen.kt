package com.example.myapplication.cards.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.cards.navigation.CardsRoutes
import com.example.myapplication.core.ui.components.BasicDialog
import com.example.myapplication.core.ui.components.CardItem
import com.example.myapplication.core.ui.theme.AppTypography
import com.example.myapplication.core.ui.theme.CardIconHeight
import com.example.myapplication.core.ui.theme.CornerRadiusMedium
import com.example.myapplication.core.ui.theme.PaddingBase
import com.example.myapplication.core.ui.theme.primaryLight
import com.example.myapplication.core.ui.theme.surfaceContainerLight
import org.koin.androidx.compose.koinViewModel

const val CARD_BALANCE_DEF = 100_000_000L
const val FRACTION_05 = 0.5f

@Composable
fun CardsScreen(
    navController: NavHostController,
    viewModel: CardsViewModel = koinViewModel(),
) {
    val cardsState = viewModel.cardsState.collectAsState().value
    val openCardDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        BasicDialog(
            visible = openCardDialog.value,
            onDismissRequest = { openCardDialog.value = false },
            onConfirmation = { openCardDialog.value = false },
            dialogTitle = stringResource(R.string.card_dialog_title),
            confirmButtonText = stringResource(R.string.card_dialog_confirm),
            dismissButtonText = stringResource(R.string.card_dialog_dismiss),
        )

        Spacer(modifier = Modifier.height(24.dp))

        when (cardsState) {
            is CardsState.Empty -> {
                Column(
                    modifier = Modifier
                        .fillMaxHeight(FRACTION_05)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CardItem(
                        cardHolderName = stringResource(R.string.card_holder_default),
                        cardBalance = CARD_BALANCE_DEF.toBigDecimal(),
                        cardType = stringResource(R.string.card_type_default),
                        cardNumber = stringResource(R.string.card_number_default)
                    ) {
                        openCardDialog.value = true
                    }
                }
            }

            is CardsState.Cards -> {
                val cards = cardsState.cards
                val pagerState = rememberPagerState(pageCount = { cards.size })

                Column(
                    modifier = Modifier
                        .fillMaxHeight(FRACTION_05)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center
                ) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 48.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        pageSpacing = 16.dp
                    ) { page ->
                        val card = cards[page]
                        CardItem(
                            cardHolderName = card.owner,
                            cardBalance = card.balance,
                            cardType = card.type,
                            cardNumber = card.id.toString()
                        ) {
                            navController.navigate("${CardsRoutes.CARD_DETAILS}/${card.id}")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Column(
            modifier = Modifier.height(188.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            CreateCardButton(
                text = stringResource(R.string.card_create_debit),
                isCredit = false
            ) { navController.navigate(CardsRoutes.CARD_DEBIT.route) }

            CreateCardButton(
                text = stringResource(R.string.card_create_credit),
                isCredit = true
            ) { navController.navigate(CardsRoutes.CARD_CREDIT.route) }
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
        onClick = onClick,
        icon = {
            val iconRes = if (isCredit) R.drawable.ic_card_credit else R.drawable.ic_card_debit
            val contentDesc = if (isCredit) {
                stringResource(R.string.card_create_credit)
            } else {
                stringResource(R.string.card_create_debit)
            }

            Image(
                painterResource(iconRes),
                contentDesc,
                modifier = Modifier.size(CardIconHeight)
            )
        },
        text = {
            Text(
                color = primaryLight,
                textAlign = TextAlign.Start,
                text = text,
                maxLines = 1,
                style = AppTypography.bodyMedium.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500
                )
            )
        },
    )
}
