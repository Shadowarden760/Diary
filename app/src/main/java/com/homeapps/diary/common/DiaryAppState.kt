package com.homeapps.diary.common

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.homeapps.diary.common.navigation.DiaryRoute
import com.homeapps.diary.common.navigation.TopLevelBackStack
import com.homeapps.diary.ui.theme.ThemeViewModel
import com.homeapps.diary.utils.DiarySnackBarManager
import io.github.themeanimator.ThemeAnimationFormat
import io.github.themeanimator.ThemeAnimationState
import io.github.themeanimator.rememberThemeAnimationState
import kotlinx.coroutines.CoroutineScope
import org.koin.androidx.compose.koinViewModel

class DiaryAppState(
    val topLevelBackStack: TopLevelBackStack<DiaryRoute>,
    val snackBarManager: DiarySnackBarManager,
    val themeAnimationState: ThemeAnimationState
)

@Composable
fun rememberAppState(
    backStack: TopLevelBackStack<DiaryRoute>,
    scope: CoroutineScope = rememberCoroutineScope(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    animationState: ThemeAnimationState = rememberThemeAnimationState(
        themeProvider = koinViewModel<ThemeViewModel>(),
        format = ThemeAnimationFormat.CircularAroundPress
    )
) = remember(scope, backStack, snackbarHostState) {
    DiaryAppState(
        topLevelBackStack = backStack,
        snackBarManager = DiarySnackBarManager(scope, snackbarHostState),
        themeAnimationState = animationState
    )
}