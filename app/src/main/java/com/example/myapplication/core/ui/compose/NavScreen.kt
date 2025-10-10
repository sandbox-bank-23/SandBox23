package com.example.myapplication.core.ui.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.myapplication.core.ui.theme.NavBarSize
import com.example.myapplication.core.ui.theme.PinPadBackgroundColor
import com.example.myapplication.core.ui.theme.RoundedCornerShapeSelector
import com.example.myapplication.core.ui.theme.secondaryContainerDark

enum class Routes(val route: String) {
    CARDS("cards"),
    FINANCE("finance"),
    TRANSFERS("transactions"),
    HISTORY("history"),
    PROFILE("profile")
}

const val ANIMATION_DELAY = 500
const val ZERO_DELAY = 0

data class BottomBarItem(
    val label: String,
    val icon: Int,
    val route: String
)

@Composable
fun NavScreen() {
    val bottomBarRoutes = listOf(
        BottomBarItem(
            label = stringResource(R.string.cards),
            icon = R.drawable.ic_cards,
            route = Routes.CARDS.route
        ),
        BottomBarItem(
            label = stringResource(R.string.finances),
            icon = R.drawable.ic_finance,
            route = Routes.FINANCE.route
        ),
        BottomBarItem(
            label = stringResource(R.string.transactions),
            icon = R.drawable.ic_transfers,
            route = Routes.TRANSFERS.route
        ),
        BottomBarItem(
            label = stringResource(R.string.history),
            icon = R.drawable.ic_history,
            route = Routes.HISTORY.route
        ),
        BottomBarItem(
            label = stringResource(R.string.profile),
            icon = R.drawable.ic_profile,
            route = Routes.PROFILE.route
        )
    )

    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController, bottomBarRoutes) }
    ) { innerPadding ->
        NavHostContent(navController, innerPadding)
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, bottomBarRoutes: List<BottomBarItem>) {
    val bottomRoutes = bottomBarRoutes.map { it.route }
    val showBottomBar = remember { mutableStateOf(true) }
    showBottomBar.value = navController.currentBackStackEntryAsState().value?.destination?.route in bottomRoutes

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
        NavigationBarContent(navController, bottomBarRoutes)
    }
}

@Composable
fun NavigationBarContent(navController: NavHostController, bottomBarRoutes: List<BottomBarItem>) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
        tonalElevation = 0.dp,
        modifier = Modifier.defaultMinSize(minHeight = NavBarSize)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        val isDarkTheme = isSystemInDarkTheme()

        bottomBarRoutes.forEach { item ->
            val selected = currentDestination?.route == item.route
            val highlightColor by animateColorAsState(
                targetValue = when {
                    selected && isDarkTheme -> secondaryContainerDark
                    selected && !isDarkTheme -> PinPadBackgroundColor
                    else -> Color.Transparent
                },
                animationSpec = if (selected) tween(ANIMATION_DELAY) else tween(ZERO_DELAY),
                label = "highlight"
            )

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
                            .clip(RoundedCornerShape(RoundedCornerShapeSelector))
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
                interactionSource = remember { NoRippleInteractionSource() }
            )
        }
    }
}

@Composable
fun NavHostContent(navController: NavHostController, padding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Routes.CARDS.route,
        modifier = Modifier.padding(padding)
    ) {
        mapsScreenNavigation()
        financeScreenNavigation()
        transfersScreenNavigation()
        historyScreenNavigation()
        profileScreenNavigation()
    }
}

// Поменять на страницы после готовности
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

fun NavGraphBuilder.mapsScreenNavigation() {
    composable(Routes.CARDS.route) { CardsScreen() }
}

fun NavGraphBuilder.financeScreenNavigation() {
    composable(Routes.FINANCE.route) { FinanceScreen() }
}

fun NavGraphBuilder.transfersScreenNavigation() {
    composable(Routes.TRANSFERS.route) { TransfersScreen() }
}

fun NavGraphBuilder.historyScreenNavigation() {
    composable(Routes.HISTORY.route) { HistoryScreen() }
}

fun NavGraphBuilder.profileScreenNavigation() {
    composable(Routes.PROFILE.route) { ProfileScreen() }
}