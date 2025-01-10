package com.specialtech.diary.ui.features.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.specialtech.diary.common.navigation.NavigationBarSection
import com.specialtech.diary.ui.theme.MainDark
import com.specialtech.diary.ui.theme.MainOrange

@Composable
fun BottomBar(navHostController: NavHostController) {
    val backStackEntry = navHostController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry.value?.destination
    NavigationBar(containerColor = MainDark) {
        NavigationBarSection.sections.forEach { section ->
            val selected = currentDestination?.hierarchy?.any { it.route == section.route } == true
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(section.icon),
                        contentDescription = null
                    )
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = MainOrange,
                    unselectedIconColor = Color.White,
                    selectedTextColor = MainOrange,
                    unselectedTextColor = Color.Transparent,
                    selectedIndicatorColor = Color.Transparent,
                    disabledIconColor = MainOrange,
                    disabledTextColor = Color.Transparent
                ),
                label = {
                    Text(
                        text = stringResource(section.title)
                    )
                },
                selected = selected,
                onClick = {
                    navHostController.navigate(section.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navHostController.graph.findStartDestination().id) {
                            saveState = true
                        }
                    }
                }
            )
        }
    }
}