package com.droidknights.app2023.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.droidknights.app2023.feature.home.navigation.HomeRoute
import com.droidknights.app2023.feature.home.navigation.navigateHome
import com.droidknights.app2023.feature.setting.navigation.SettingRoute
import com.droidknights.app2023.feature.setting.navigation.navigateSetting

internal class MainNavigator(
    val navController: NavHostController,
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination
    val startDestination: String
        @Composable get() = currentDestination?.route ?: HomeRoute.route

    val currentTab: MainTab?
        @Composable get() = when (currentDestination?.route) {
            HomeRoute.route -> MainTab.HOME
            SettingRoute.route -> MainTab.SETTING
            "temp" -> MainTab.TEMP
            else -> null
        }


    fun navigate(tab: MainTab) {
        val navOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }

        when (tab) {
            MainTab.SETTING -> navController.navigateSetting(navOptions)
            MainTab.HOME -> navController.navigateHome(navOptions)
            MainTab.TEMP -> navController.navigate("temp") // TODO: ???
        }
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}
