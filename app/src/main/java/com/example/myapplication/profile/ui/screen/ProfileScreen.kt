package com.example.myapplication.profile.ui.screen

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.core.ui.components.SimpleIconDialog
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
import com.example.myapplication.profile.ui.state.Features
import com.example.myapplication.profile.ui.state.ProfileState
import com.example.myapplication.profile.ui.viewmodel.ProfileViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.DecimalFormat

@Suppress("CognitiveComplexMethod")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = koinViewModel<ProfileViewModel>()
) {
    val profileData = viewModel.profileState.collectAsState().value
    val isLatestVersion = viewModel.isLatestVersion.collectAsState().value

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
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
        SimpleIconDialog(
            visible = isLatestVersion != 0,
            onDismissRequest = { viewModel.dismissDialog() },
            dialogTitle = when (isLatestVersion) {
                1 -> stringResource(R.string.latest_version)
                -1 -> stringResource(R.string.not_latest_version)
                else -> ""
            },
            dismissButtonText = stringResource(R.string.ok),
            icon = ImageVector.vectorResource(R.drawable.ic_credit_open_ok)
        )

        when (profileData) {
            is ProfileState.Loading -> Unit
            is ProfileState.ProfileData -> {
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
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ProfileCard(title = "Клиент", subtitle = profileData.userName, id = profileData.userId)
                        Spacer(modifier = Modifier.height(12.dp))
                        InfoCardsGroup(profileData)
                        Spacer(modifier = Modifier.height(16.dp))
                        val isDark = isSystemInDarkTheme()
                        val switchesCardBackgroundColor =
                            if (isDark) onSecondaryContainerDark else MaterialTheme.colorScheme.secondaryFixedDim
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = switchesCardBackgroundColor)
                        ) {
                            Column {
                                SettingSwitchRow(
                                    label = stringResource(R.string.themeSwitchTittle),
                                    enabled = profileData.features and Features.THEME.flag != 0,
                                    checked = profileData.isDarkTheme
                                ) { viewModel.changeTheme() }
                                SettingSwitchRow(
                                    label = stringResource(R.string.languageSwitchTittle),
                                    enabled = profileData.features and Features.LANG.flag != 0,
                                    checked = profileData.isLangEnglish
                                ) {}
                                SettingSwitchRow(
                                    label = stringResource(R.string.notificationSwitchTittle),
                                    enabled = profileData.features and Features.NOTIFICATIONS.flag != 0,
                                    checked = profileData.isNotificationsEnabled
                                ) {}
                                SettingSwitchRow(
                                    label = stringResource(R.string.faceIdSwitchTittle),
                                    enabled = false,
                                    checked = profileData.isFaceIdEnabled
                                ) {}
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        val backgroundButtonColor = if (isDark) inversePrimaryLight else tertiaryContainerLight
                        val buttonBorderColor = if (isDark) onPrimaryLight else onTertiaryLight
                        val buttonTextColor = if (isDark) onPrimaryDark else onPrimaryLight
                        Button(
                            onClick = { viewModel.requestAppUpdate() },
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
                            onClick = {
                                viewModel.requestLogOut()
                                navController.navigate("auth/registration") {
                                    popUpTo(0) {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }
                            },
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
fun InfoCardsGroup(profileData: ProfileState.ProfileData) {
    val isDark = isSystemInDarkTheme()
    val backgroundColor = if (isDark) surfaceLight else surfaceContainerHighLight
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column {
            InfoRow(
                stringResource(R.string.commonBalance),
                "${DecimalFormat("###,###").format(profileData.totalBalance)} ₽"
            )
            DividerLine()
            val creditCardsBalancePair = when (profileData.creditCardsBalance) {
                0L -> {
                    Pair(stringResource(R.string.noCreditCardsBalance), "")
                }
                else -> {
                    Pair(
                        stringResource(R.string.creditCardsBalance),
                        "${DecimalFormat("###,###").format(profileData.creditCardsBalance)} ₽"
                    )
                }
            }
            InfoRow(creditCardsBalancePair.first, creditCardsBalancePair.second)
            DividerLine()
            InfoRow(
                stringResource(R.string.depositBalance),
                "${DecimalFormat("###,###").format(profileData.depositsBalance)} ₽"
            )
            DividerLine()
            InfoRow(
                stringResource(R.string.creditBalance),
                "${DecimalFormat("###,###").format(profileData.loansBalance)} ₽"
            )
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
fun SettingSwitchRow(
    label: String,
    enabled: Boolean,
    checked: Boolean,
    onClickAction: (checked: Boolean) -> Unit
) {
    var state by remember { mutableStateOf(checked) }
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
            checked = state,
            onCheckedChange = {
                state = it
                onClickAction(state)
            },
            enabled = enabled,
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
    HorizontalDivider(
        color = outlineLight.copy(alpha = 0.5f),
        thickness = 0.5.dp,
        modifier = Modifier.fillMaxWidth()
    )
}