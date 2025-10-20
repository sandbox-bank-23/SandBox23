@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.myapplication.loansanddeposits.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.core.ui.theme.AppTypography
import com.example.myapplication.core.ui.theme.PaddingBase
import com.example.myapplication.loansanddeposits.ui.state.CreditUi
import com.example.myapplication.loansanddeposits.ui.state.DepositUi
import com.example.myapplication.loansanddeposits.ui.state.LoansDepositsState
import com.example.myapplication.loansanddeposits.ui.viewmodel.LoansDepositsViewModel
import org.koin.androidx.compose.koinViewModel
import com.example.myapplication.loansanddeposits.ui.state.CreditType as VmCreditType

data class Deposit(val id: Long, val title: String, val balanceText: String, val percentType: Int)
data class Credit(
    val id: Long,
    val name: String,
    val amountText: String,
    val type: VmCreditType
)

@Composable
fun LoansDepositsScreen(
    navController: NavHostController,
    viewModel: LoansDepositsViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onApplyCreditClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { viewModel.requestData() }

    val depositsUi: List<DepositUi>
    val creditsUi: List<CreditUi>
    when (val s = state) {
        is LoansDepositsState.Content -> {
            depositsUi = s.deposits
            creditsUi = s.credits
        }

        is LoansDepositsState.Loading, is LoansDepositsState.Offline, is LoansDepositsState.Error -> {
            depositsUi = emptyList()
            creditsUi = emptyList()
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
        topBar = {
            TopAppBar(
                windowInsets = TopAppBarDefaults.windowInsets,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
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

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = PaddingBase),
            contentPadding = PaddingValues(vertical = PaddingBase),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { SectionHeader(text = stringResource(R.string.deposits)) }

            items(depositsUi, key = { it.id }) { item ->
                DepositItem(
                    item = Deposit(item.id, item.title, item.balanceText, item.percentType),
                    iconRes = R.drawable.ic_deposit,
                    onClick = { /* открыть вклад по id */ }
                )
            }

            item {
                PrimaryWideButton(
                    text = stringResource(R.string.open_deposit),
                    onClick = onApplyCreditClick
                )
            }

            item {
                Spacer(Modifier.height(4.dp))
                SectionHeader(text = stringResource(R.string.loans))
            }

            items(creditsUi, key = { it.id }) { item ->
                CreditItem(
                    item = Credit(item.id, item.name, item.amountText, item.type)
                )
            }

            item {
                PrimaryWideButton(
                    text = stringResource(R.string.open_loan),
                    onClick = onApplyCreditClick
                )
            }

            item { Spacer(Modifier.height(8.dp)) }
        }
    }
}

@Composable
private fun SectionHeader(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
        color = MaterialTheme.colorScheme.primary,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
    )
}

@Composable
private fun PrimaryWideButton(text: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(100.dp),
        onClick = onClick
    ) {
        Text(
            text = text,
            style = AppTypography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onPrimary
            )
        )
    }
}

@Composable
fun DepositItem(
    item: Deposit,
    iconRes: Int,
    onClick: (depositId: Long) -> Unit
) {
    val shape = RoundedCornerShape(12.dp)
    val backgroundColor =
        if (item.id == 1L) Color(0xFFFEF7FF) else (MaterialTheme.colorScheme.surfaceContainerLowest)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .clickable { onClick(item.id) },
        shape = shape,
        color = backgroundColor
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
        ) {
            Card(
                modifier = Modifier.padding(start = 22.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Image(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFFFFFFFF)),
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 31.dp, top = 16.dp, bottom = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = item.title,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = item.balanceText,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Text(
                modifier = Modifier.padding(end = 81.dp),
                text = "${item.percentType}%",
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun CreditItem(
    item: Credit,
    iconAutoRes: Int = R.drawable.ic_car,
    iconMortgageRes: Int = R.drawable.ic_mortage
) {
    val shape = RoundedCornerShape(12.dp)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape),
        shape = shape,
        color = MaterialTheme.colorScheme.surfaceContainerLowest
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
        ) {
            Card(
                modifier = Modifier.padding(start = 22.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Image(
                    modifier = Modifier
                        .size(40.dp)
                        .background(MaterialTheme.colorScheme.surfaceContainerLowest),
                    painter = painterResource(
                        id = when (item.type) {
                            VmCreditType.Auto -> iconAutoRes
                            VmCreditType.Mortgage -> iconMortgageRes
                        }
                    ),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 24.dp, top = 16.dp, end = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = item.name,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = item.amountText,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "LoansDepositsScreen")
@Composable
fun LoansDepositsScreenPreview() {
    MaterialTheme {
        LoansDepositsScreen(
            navController = rememberNavController(),
            viewModel = LoansDepositsViewModel(),
            onBackClick = {},
            onApplyCreditClick = {}
        )
    }
}
