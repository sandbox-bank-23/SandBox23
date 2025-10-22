@file:OptIn(ExperimentalMaterial3Api::class)
@file:Suppress("UnusedParameter")

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.core.ui.theme.AppTypography
import com.example.myapplication.core.ui.theme.CardIconHeight
import com.example.myapplication.core.ui.theme.CornerRadiusRegular
import com.example.myapplication.core.ui.theme.Height100
import com.example.myapplication.core.ui.theme.Height56
import com.example.myapplication.core.ui.theme.Height72
import com.example.myapplication.core.ui.theme.Padding12dp
import com.example.myapplication.core.ui.theme.Padding16dp
import com.example.myapplication.core.ui.theme.Padding22dp
import com.example.myapplication.core.ui.theme.Padding24dp
import com.example.myapplication.core.ui.theme.Padding31dp
import com.example.myapplication.core.ui.theme.Padding81dp
import com.example.myapplication.core.ui.theme.PaddingBase
import com.example.myapplication.core.ui.theme.PaddingQuarter
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
            verticalArrangement = Arrangement.spacedBy(Padding12dp)
        ) {
            item { SectionHeader(text = stringResource(R.string.deposits)) }

            if (depositsUi.isEmpty()) {
                item {
                    EmptyStateCard(
                        iconRes = R.drawable.ic_deposit,
                        message = stringResource(R.string.no_deposits_message)
                    )
                }
            } else {
                items(depositsUi, key = { it.id }) { item ->
                    DepositItem(
                        item = Deposit(item.id, item.title, item.balanceText, item.percentType),
                        iconRes = R.drawable.ic_deposit,
                        onClick = { /* открыть вклад по id */ }
                    )
                }
            }

            item {
                PrimaryWideButton(
                    text = stringResource(R.string.open_deposit),
                    onClick = onApplyCreditClick
                )
            }

            item {
                Spacer(Modifier.height(PaddingQuarter))
                SectionHeader(text = stringResource(R.string.loans))
            }

            if (creditsUi.isEmpty()) {
                item {
                    EmptyStateCard(
                        iconRes = R.drawable.ic_mortage,
                        message = stringResource(R.string.no_loans_message)
                    )
                }
            } else {
                items(creditsUi, key = { it.id }) { item ->
                    CreditItem(
                        item = Credit(item.id, item.name, item.amountText, item.type)
                    )
                }
            }

            item {
                PrimaryWideButton(
                    text = stringResource(R.string.open_loan),
                    onClick = onApplyCreditClick
                )
            }

            item { Spacer(Modifier.height(CornerRadiusRegular)) }
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
        modifier = Modifier.padding(top = PaddingQuarter, bottom = PaddingQuarter)
    )
}

@Composable
private fun PrimaryWideButton(text: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(Height56),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(Height100),
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
    val shape = RoundedCornerShape(Padding12dp)
    val backgroundColor = MaterialTheme.colorScheme.surfaceContainerLowest

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
                .height(Height72)
        ) {
            Card(
                modifier = Modifier.padding(start = Padding22dp),
                shape = RoundedCornerShape(CornerRadiusRegular)
            ) {
                Image(
                    modifier = Modifier
                        .size(CardIconHeight)
                        .background(MaterialTheme.colorScheme.surfaceContainerLowest),
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = Padding31dp, top = Padding16dp, bottom = Padding16dp)
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
                modifier = Modifier.padding(end = Padding81dp),
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
    val shape = RoundedCornerShape(Padding12dp)

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
                .height(Height72)
        ) {
            Card(
                modifier = Modifier.padding(start = Padding22dp),
                shape = RoundedCornerShape(CornerRadiusRegular)
            ) {
                Image(
                    modifier = Modifier
                        .size(CardIconHeight)
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
                    .padding(start = Padding24dp, top = Padding16dp, end = Padding16dp)
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

@Composable
private fun EmptyStateCard(
    iconRes: Int,
    message: String,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(Padding12dp)
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape),
        shape = shape,
        color = MaterialTheme.colorScheme.surfaceContainerLowest
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(Height72)
                .padding(horizontal = Padding22dp)
        ) {
            Card(shape = RoundedCornerShape(CornerRadiusRegular)) {
                Image(
                    modifier = Modifier
                        .size(CardIconHeight)
                        .background(MaterialTheme.colorScheme.surfaceContainerLowest),
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )
            }
            Text(
                text = message,
                style = AppTypography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(start = Padding24dp)
                    .weight(1f)
            )
        }
    }
}
