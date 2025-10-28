package com.example.myapplication.profile.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.core.ui.theme.AppTypography
import com.example.myapplication.core.ui.theme.NavTextInactiveLight
import com.example.myapplication.core.ui.theme.inversePrimaryLight
import com.example.myapplication.core.ui.theme.lightPrimary
import com.example.myapplication.core.ui.theme.onPrimaryDark
import com.example.myapplication.core.ui.theme.onPrimaryLight
import com.example.myapplication.core.ui.theme.onSecondaryContainerDark
import com.example.myapplication.core.ui.theme.onSurfaceLight
import com.example.myapplication.core.ui.theme.onTertiaryLight
import com.example.myapplication.core.ui.theme.outlineDark
import com.example.myapplication.core.ui.theme.outlineLight
import com.example.myapplication.core.ui.theme.surfaceContainerHighLight
import com.example.myapplication.core.ui.theme.surfaceLight
import com.example.myapplication.core.ui.theme.tertiaryContainerLight

@Suppress("CognitiveComplexMethod")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController?) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.height(64.dp),
                windowInsets = WindowInsets(0),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                ),
                navigationIcon = {
                    IconButton(onClick = { navController?.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(R.string.myProfile),
                        style = AppTypography.titleLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = innerPadding.calculateTopPadding() + 16.dp,
                    bottom = 25.dp
                )
        ) {
            // Контент, который может скроллиться
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                ProfileCard(title = "Клиент", subtitle = "Ivanova Oksana", id = "4785 3216")
                Spacer(modifier = Modifier.height(12.dp))
                InfoCardsGroup()
                Spacer(modifier = Modifier.height(16.dp))
                SettingSwitchGroup()
                Spacer(modifier = Modifier.height(16.dp))


                val isDark = isSystemInDarkTheme()
                val backgroundButtonColor = if (isDark) inversePrimaryLight else tertiaryContainerLight
                val buttonBorderColor = if (isDark) onPrimaryLight else onTertiaryLight
                val buttonTextColor = if (isDark) onPrimaryDark else onPrimaryLight
                Button(
                    onClick = {},
                    modifier = Modifier
                        .width(380.dp)
                        .height(64.dp),
                    shape = RoundedCornerShape(100.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = backgroundButtonColor)
                ) {
                    Text(
                        text = stringResource(R.string.updateApp),
                        color = buttonTextColor,
                        style = MaterialTheme.typography.labelMedium
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                OutlinedButton(
                    onClick = {},
                    modifier = Modifier
                        .width(380.dp)
                        .height(64.dp),
                    shape = RoundedCornerShape(100.dp),
                    border = BorderStroke(2.dp, buttonBorderColor)
                ) {
                    Text(
                        text = stringResource(R.string.exitFromAccount),
                        color = buttonBorderColor,
                        style = MaterialTheme.typography.labelMedium
                    )
                }

            }
        }
    }
}

@Composable
fun ProfileCard(title: String, subtitle: String, id: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = onSecondaryContainerDark),
        shape = RoundedCornerShape(18.dp)
    ) {
        Column {
            InfoSection(title, subtitle)

            DividerLine()

            InfoSection("ID", id)
        }
    }
}

@Composable
private fun InfoSection(label: String, value: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = onSurfaceLight
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = NavTextInactiveLight
        )
    }
}

@Composable
fun InfoCardsGroup() {
    val isDark = isSystemInDarkTheme()
    val backgroundColor = if (isDark) surfaceLight else surfaceContainerHighLight
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column {
            InfoRow(stringResource(R.string.commonBalance), "50 000 ₽")
            DividerLine()
            InfoRow(stringResource(R.string.debtsBalance), "нет")
            DividerLine()
            InfoRow(stringResource(R.string.depositBalance), "40 000 ₽")
            DividerLine()
            InfoRow(stringResource(R.string.creditBalance), "8 770 000 ₽")
        }
    }
}

@Composable
private fun InfoRow(title: String, value: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = "$title $value",
            style = MaterialTheme.typography.bodyLarge,
            color = onSurfaceLight,
            maxLines = Int.MAX_VALUE
        )
    }
}

@Composable
fun SettingSwitchGroup() {
    val isDark = isSystemInDarkTheme()
    val backgroundColor = if (isDark) onSecondaryContainerDark else MaterialTheme.colorScheme.secondaryFixedDim
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column {
            SettingSwitchRow(stringResource(R.string.themeSwitchTittle), remember { mutableStateOf(false) })
            SettingSwitchRow(stringResource(R.string.languageSwitchTittle), remember { mutableStateOf(false) })
            SettingSwitchRow(stringResource(R.string.notificationSwitchTittle), remember { mutableStateOf(false) })
            SettingSwitchRow(stringResource(R.string.faceIdSwitchTittle), remember { mutableStateOf(true) })
        }
    }
}

@Composable
fun SettingSwitchRow(label: String, state: MutableState<Boolean>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge,
            color = onSurfaceLight
        )

        Spacer(modifier = Modifier.width(16.dp))

        Switch(
            checked = state.value,
            onCheckedChange = { state.value = it },
            colors = SwitchDefaults.colors(
                checkedTrackColor = lightPrimary,
                checkedThumbColor = onPrimaryLight,
                uncheckedTrackColor = outlineDark,
                uncheckedThumbColor = outlineLight,
                checkedBorderColor = lightPrimary,
                uncheckedBorderColor = outlineLight
            )
        )
    }
}

@Composable
fun DividerLine() {
    Divider(
        color = outlineLight.copy(alpha = 0.5f),
        thickness = 0.5.dp,
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    MaterialTheme {
        Box(modifier = Modifier.height(2000.dp)) {
            ProfileScreen(null)
        }
    }
}