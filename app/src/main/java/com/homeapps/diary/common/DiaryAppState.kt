package com.homeapps.diary.common

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.homeapps.diary.ui.theme.ThemeViewModel
import com.homeapps.diary.utils.DiarySnackBarManager
import io.github.themeanimator.ThemeAnimationFormat
import io.github.themeanimator.ThemeAnimationState
import io.github.themeanimator.rememberThemeAnimationState
import kotlinx.coroutines.CoroutineScope
import org.koin.androidx.compose.koinViewModel

class DiaryAppState(
    val navController: NavHostController,
    val snackBarManager: DiarySnackBarManager,
    val themeAnimationState: ThemeAnimationState
)

@Composable
fun rememberAppState(
    scope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    animationState: ThemeAnimationState = rememberThemeAnimationState(
        themeProvider = koinViewModel<ThemeViewModel>(),
        format = ThemeAnimationFormat.CircularAroundPress
    )
) = remember(scope, navController, snackbarHostState) {
    DiaryAppState(
        navController = navController,
        snackBarManager = DiarySnackBarManager(scope, snackbarHostState),
        themeAnimationState = animationState
    )
}