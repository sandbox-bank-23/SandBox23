package com.example.myapplication.deposits.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.core.ui.theme.AppTypography
import com.example.myapplication.core.ui.theme.CornerRadiusMedium
import com.example.myapplication.core.ui.theme.Padding16dp
import com.example.myapplication.core.ui.theme.Padding18dp
import com.example.myapplication.core.ui.theme.Padding22dp
import com.example.myapplication.core.ui.theme.Padding24dp
import com.example.myapplication.core.ui.theme.Padding28dp
import com.example.myapplication.core.ui.theme.Padding34dp
import com.example.myapplication.core.ui.theme.Padding38dp
import com.example.myapplication.core.ui.theme.Padding48dp
import com.example.myapplication.core.ui.theme.Padding8dp
import com.example.myapplication.core.ui.theme.Padding9dp
import com.example.myapplication.core.ui.theme.PaddingBase
import com.example.myapplication.core.ui.theme.PaddingQuarter
import com.example.myapplication.core.ui.theme.Width12
import com.example.myapplication.deposits.ui.utils.toMoneyString
import java.math.BigDecimal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepositDetailScreen(
    modifier: Modifier = Modifier,
    name: String = "",
    onBalanceUpClick: () -> Unit = {},
    onCloseClick: () -> Unit = {},
    onOperationsClick: () -> Unit = {},
    onPercentClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                windowInsets = WindowInsets(0),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                ),
                title = {
                    Text(
                        text = stringResource(R.string.deposit_opening),
                        style = AppTypography.titleLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = modifier.padding(start = PaddingBase)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { /* navController.popBackStack() */ },
                        modifier = modifier
                            .size(Padding48dp)
                            .padding(start = PaddingBase)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.arrow_back),
                            modifier = modifier.size(Padding24dp)
                        )
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(horizontal = Padding16dp)
        ) {

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = Padding38dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = modifier.padding(start = 41.dp, end = Padding16dp),
                    text = stringResource(R.string.happy_future),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )

                Icon(
                    modifier = modifier.padding(14.dp),
                    painter = painterResource(R.drawable.ic_pencil),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(PaddingQuarter))

            // сумма
            Text(
                modifier = modifier.padding(start = 41.dp),
                text = stringResource(
                    R.string.deposit_amount,
                    BigDecimal(34556.45).toMoneyString()
                ), // TODO Здесь заменить хардкод-заглушку на переменную
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = modifier.padding(start = 41.dp),
                text = stringResource(
                    R.string.year_percent,
                    "12"
                ), // TODO Здесь заменить хардкод-заглушку на переменную
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(Padding28dp))

            // --- Кнопки ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Padding8dp),
                horizontalArrangement = Arrangement.spacedBy(Padding9dp)
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(CornerRadiusMedium),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer),
                    onClick = onBalanceUpClick
                ) {
                    Text(
                        modifier = modifier.padding(vertical = PaddingBase),
                        text = stringResource(R.string.deposit_top_up),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Button(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(CornerRadiusMedium),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer),
                    onClick = onCloseClick
                ) {
                    Text(
                        modifier = modifier.padding(vertical = PaddingBase),
                        text = stringResource(R.string.close_deposit),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(Padding34dp))

            // --- Операции по вкладу ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOperationsClick() }
                    .padding(vertical = Padding22dp, horizontal = Padding28dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = modifier.padding(end = Padding8dp),
                    painter = painterResource(id = R.drawable.ic_deposit),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(Width12))
                Text(
                    text = stringResource(R.string.deposit_operations),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(Padding18dp))

            // --- Начисленные проценты ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onPercentClick() }
                    .padding(vertical = Padding22dp, horizontal = Padding28dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = modifier.padding(end = Padding8dp),
                    painter = painterResource(id = R.drawable.ic_percent),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(Width12))
                Text(
                    text = "Начисленные проценты",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DepositDetailsScreenPreview() {
    MaterialTheme {
        DepositDetailScreen()
    }
}

