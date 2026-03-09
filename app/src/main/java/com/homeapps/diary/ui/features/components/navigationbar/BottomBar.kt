package com.homeapps.diary.ui.features.components.navigationbar

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.homeapps.diary.common.DiaryAppState
import com.homeapps.diary.common.navigation.DiaryRoute
import com.homeapps.diary.ui.TopLevelBackStack

@Composable
fun BottomBar(appState: DiaryAppState, bottomItems: List<DiaryRoute>) {
    NavigationBar {
        bottomItems.forEach { section ->
            val selected = appState.topLevelBackStack.topLevelKey.value == section
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = section.icon),
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = section.title),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                selected = selected,
                onClick = {
                    appState.topLevelBackStack.switchTopLevel(key = section)
                }
            )
        }
    }
}