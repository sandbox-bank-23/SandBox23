package com.example.myapplication.loansanddeposits.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.core.ui.theme.AppTypography
import com.example.myapplication.core.ui.theme.PaddingBase

data class Deposit(val id: Long, val title: String, val balanceText: String, val percentType: Int)
data class Credit(val id: Long, val name: String, val amountText: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoansDepositsScreen(
    navController: NavHostController,
    onBackClick: () -> Unit,
    onApplyCreditClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                windowInsets = WindowInsets(0),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground
                ),
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
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
                },
                title = {
                    Text(
                        text = stringResource(R.string.loans_and_deposits),
                        style = AppTypography.titleLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = PaddingBase)
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = PaddingBase, vertical = PaddingBase)
                .fillMaxWidth()
        ) {
            // Deposit block
            DepositItem(
                item = Deposit(
                    id = 1L,
                    title = "Накопительный вклад",
                    balanceText = "10 000,00 ₽",
                    percentType = 8
                ),
                iconRes = R.drawable.ic_deposit,
                onClick = { /* open deposit by id */ }
            )
            DepositItem(
                item = Deposit(
                    id = 1L,
                    title = "До восстребования",
                    balanceText = "30 000,00 ₽",
                    percentType = 4
                ),
                iconRes = R.drawable.ic_deposit,
                onClick = { /* open deposit by id */ }
            )

            Spacer(Modifier.height(8.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(100.dp),
                onClick = onApplyCreditClick
            ) {
                Text(
                    text = stringResource(R.string.open_deposit),
                    style = AppTypography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }

            Spacer(Modifier.height(16.dp))

            CreditItem(
                item = Credit(
                    id = 2L,
                    name = "Автокредит",
                    amountText = "15 июня спишем 23 700 ₽"
                )
            )
            CreditItem(
                item = Credit(
                    id = 2L,
                    name = "Ипотека",
                    amountText = "7 июня спишем 128 700 ₽"
                )
            )

            Spacer(Modifier.height(8.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(100.dp),
                onClick = onApplyCreditClick
            ) {
                Text(
                    text = stringResource(R.string.open_loan),
                    style = AppTypography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }
    }
}

@Composable
fun DepositItem(
    item: Deposit,
    iconRes: Int,
    onClick: (depositId: Long) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .padding(vertical = 8.dp)
            .clickable { onClick(item.id) }
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Card(
            modifier = Modifier.padding(start = 22.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .background(MaterialTheme.colorScheme.background),
                painter = painterResource(id = iconRes),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
        }
        Column(
            modifier = Modifier
                .padding(start = 31.dp, top = 16.dp, end = 31.dp)
                .weight(1f)
        ) {
            Text(
                text = item.title,
                maxLines = 1,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                ),
            )
            Text(
                text = item.balanceText,
                maxLines = 1,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                ),
            )
        }
        Text(
            modifier = Modifier.padding(end = 24.dp),
            text = "${item.percentType} %",
            maxLines = 1,
            style = TextStyle(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            ),
        )
    }
}

@Composable
fun CreditItem(
    item: Credit,
    iconAutoRes: Int = R.drawable.ic_car,
    iconMortgageRes: Int = R.drawable.ic_mortage
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .padding(vertical = 8.dp)
    ) {
        Card(
            modifier = Modifier.padding(start = 22.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .background(MaterialTheme.colorScheme.background),
                painter = painterResource(
                    id = if (item.name == "Автокредит") iconAutoRes else iconMortgageRes
                ),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
        }
        Column(
            modifier = Modifier
                .padding(start = 31.dp, top = 16.dp, end = 31.dp)
                .weight(1f)
        ) {
            Text(
                text = item.name,
                maxLines = 1,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                ),
            )
            Text(
                text = item.amountText,
                maxLines = 1,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                ),
            )
        }
    }
}

@Preview(showBackground = true, name = "LoansDepositsScreen")
@Composable
fun LoansDepositsScreenPreview() {
    MaterialTheme {
        LoansDepositsScreen(
            navController = rememberNavController(),
            onBackClick = {},
            onApplyCreditClick = {}
        )
    }
}
