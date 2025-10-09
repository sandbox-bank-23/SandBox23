package com.example.myapplication.ui.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.domain.model.BottomBarItem


const val ROUTE_MAPS = "cards"
const val ROUTE_FINANCE = "finance"
const val ROUTE_TRANSFERS = "transactions"
const val ROUTE_HISTORY = "history"
const val ROUTE_PROFILE = "profile"

const val ANIMATION_DELAY = 400
const val ZERO_DELAY = 0

@Composable
fun NavScreen() {
    val navController = rememberNavController()

    val bottomBarRoutes = listOf(
        BottomBarItem(
            label = stringResource(R.string.cards),
            icon = R.drawable.ic_cards,
            route = ROUTE_MAPS
        ),
        BottomBarItem(
            label = stringResource(R.string.finances),
            icon = R.drawable.ic_finance,
            route = ROUTE_FINANCE
        ),
        BottomBarItem(
            label = "Переводы",
            icon = R.drawable.ic_transfers,
            route = ROUTE_TRANSFERS
        ),
        BottomBarItem(
            label = stringResource(R.string.finances),
            icon = R.drawable.ic_history,
            route = ROUTE_HISTORY
        ),
        BottomBarItem(
            label = "Профиль",
            icon = R.drawable.ic_profile,
            route = ROUTE_PROFILE
        )
    )

    val bottomRoutes = bottomBarRoutes.map { it.route }
    val showBottomBar = remember { mutableStateOf(true) }
    showBottomBar.value = navController.currentBackStackEntryAsState().value?.destination?.route in bottomRoutes

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar.value,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(durationMillis = ANIMATION_DELAY)
                ),
                exit = slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(durationMillis = ZERO_DELAY)
                )
            ) {
                Column {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                        tonalElevation = 0.dp,
                        modifier = Modifier.defaultMinSize(minHeight = 77.dp)
                    ) {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        val isDarkTheme = isSystemInDarkTheme()

                        bottomBarRoutes.forEach { item ->
                            val selected = currentDestination?.route == item.route

                            val highlightColor by animateColorAsState(
                                targetValue = when {
                                    selected && isDarkTheme -> Color(0xFF4A4458)
                                    selected && !isDarkTheme -> Color(0xFFE8DEF8)
                                    else -> Color.Transparent
                                },
                                label = "highlight"
                            )

                            // 🔹 отдельный interactionSource для каждой кнопки
                            val itemInteractionSource = remember { MutableInteractionSource() }

                            NavigationBarItem(
                                selected = selected,
                                onClick = {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    Box(
                                        modifier = Modifier
                                            .size(width = 56.dp, height = 32.dp)
                                            .clip(RoundedCornerShape(50))
                                            .background(highlightColor),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            painter = painterResource(id = item.icon),
                                            contentDescription = item.label,
                                            modifier = Modifier.size(24.dp),
                                            tint = Gray
                                        )
                                    }
                                },
                                label = {
                                    Text(
                                        text = item.label,
                                        maxLines = 1,
                                        modifier = Modifier.height(20.dp),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Gray
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = Gray,
                                    selectedTextColor = Gray,
                                    unselectedIconColor = Gray,
                                    unselectedTextColor = Gray,
                                    indicatorColor = Color.Transparent,
                                ),
                                alwaysShowLabel = true,
                                interactionSource = itemInteractionSource,
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ROUTE_MAPS,
            modifier = Modifier.padding(innerPadding)
        ) {
            mapsScreenNavigation(navController)
            financeScreenNavigation(navController)
            transfersScreenNavigation(navController)
            historyScreenNavigation(navController)
            profileScreenNavigation(navController)
        }
    }
}

//Поменять на страницы после готовности
@Composable
fun CardsScreen() = PlaceholderScreen("Карты")

@Composable
fun FinanceScreen() = PlaceholderScreen("Финансы")

@Composable
fun TransfersScreen() = PlaceholderScreen("Переводы")

@Composable
fun HistoryScreen() = PlaceholderScreen("История")

@Composable
fun ProfileScreen() = PlaceholderScreen("Профиль")

@Composable
fun PlaceholderScreen(title: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

fun NavGraphBuilder.mapsScreenNavigation(navController: NavHostController) {
    composable(ROUTE_MAPS) { CardsScreen() }
}

fun NavGraphBuilder.financeScreenNavigation(navController: NavHostController) {
    composable(ROUTE_FINANCE) { FinanceScreen() }
}

fun NavGraphBuilder.transfersScreenNavigation(navController: NavHostController) {
    composable(ROUTE_TRANSFERS) { TransfersScreen() }
}

fun NavGraphBuilder.historyScreenNavigation(navController: NavHostController) {
    composable(ROUTE_HISTORY) { HistoryScreen() }
}

fun NavGraphBuilder.profileScreenNavigation(navController: NavHostController) {
    composable(ROUTE_PROFILE) { ProfileScreen() }
}