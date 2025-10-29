package com.example.myapplication.loans.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.core.ui.components.SimpleTopBar
import com.example.myapplication.core.ui.theme.CornerRadiusMedium
import com.example.myapplication.core.ui.theme.Padding16dp
import com.example.myapplication.core.ui.theme.Padding18dp
import com.example.myapplication.core.ui.theme.Padding22dp
import com.example.myapplication.core.ui.theme.Padding28dp
import com.example.myapplication.core.ui.theme.Padding34dp
import com.example.myapplication.core.ui.theme.Padding38dp
import com.example.myapplication.core.ui.theme.Padding8dp
import com.example.myapplication.core.ui.theme.Padding9dp
import com.example.myapplication.core.ui.theme.PaddingBase
import com.example.myapplication.core.ui.theme.PaddingQuarter
import com.example.myapplication.core.ui.theme.Width12
import com.example.myapplication.loans.domain.model.Credit
import java.math.BigDecimal
import android.icu.text.DecimalFormat
import android.icu.text.DecimalFormatSymbols
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanDetailScreen(
    loanId: Long,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onCloseClick: () -> Unit,
    onBalanceUpClick: () -> Unit,
    loanData: Credit
) {
    Scaffold(
        topBar = {
            SimpleTopBar(title = stringResource(R.string.loans)) { navController.popBackStack() }
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
                    text = loanData.name,
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

            Text(
                modifier = modifier.padding(start = 41.dp),
                text = stringResource(
                    R.string.loan_balance,
                    loanData.balance.toMoneyString()
                ),
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = modifier.padding(start = 41.dp),
                text = "Ставка ${loanData.percent}% годовых",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(Padding28dp))

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
                        text = stringResource(R.string.make_a_payment),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Button(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(CornerRadiusMedium),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer),
                    onClick = {
                        onCloseClick()
                        navController.popBackStack()
                    }
                ) {
                    Text(
                        modifier = modifier.padding(vertical = PaddingBase),
                        text = stringResource(R.string.close),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(Padding34dp))

            LoanTransactions(
                modifier = modifier,
                painter = painterResource(R.drawable.ic_schedule),
                text = stringResource(R.string.payment_schedule),
            )

            Spacer(modifier = Modifier.height(Padding18dp))

            LoanTransactions(
                modifier = modifier,
                painter = painterResource(R.drawable.ic_deposit),
                text = stringResource(R.string.necessary_to_make),
            )
        }
    }
}

@Composable
fun LoanTransactions(
    modifier: Modifier = Modifier,
    painter: Painter,
    text: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Padding22dp, horizontal = Padding28dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = modifier.padding(end = Padding8dp),
            painter = painter,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(Width12))
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

fun BigDecimal.toMoneyString(): String {
    val formatter = DecimalFormat("#,##0.00")
    formatter.decimalFormatSymbols = DecimalFormatSymbols(Locale("ru")).apply {
        groupingSeparator = ' '   // Разделитель тысяч — пробел
        decimalSeparator = ','    // Запятая как десятичный разделитель
    }
    return formatter.format(this)
}

//@Preview(showBackground = true)
//@Composable
//fun DepositDetailsScreenPreview() {
//    val navController = rememberNavController()
//    MaterialTheme {
//        LoanDetailScreen(
//            loanId = 42,
//            modifier = Modifier,
//            navController = navController
//        )
//    }
//}

