package com.homeapps.diary.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.ui.Modifier
import com.homeapps.diary.common.navigation.DiaryNavHost
import com.homeapps.diary.common.navigation.DiaryRoute
import com.homeapps.diary.common.navigation.NavViewModel
import com.homeapps.diary.common.rememberAppState
import com.homeapps.diary.ui.features.components.BottomBar
import com.homeapps.diary.ui.theme.DiaryTheme
import io.github.themeanimator.ThemeAnimationScope
import io.github.themeanimator.theme.isDark
import org.koin.compose.viewmodel.koinViewModel

class MainActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val navViewModel: NavViewModel = koinViewModel()
            val bottomItems = listOf(DiaryRoute.Home, DiaryRoute.NoteList, DiaryRoute.Weather)
            val appState = rememberAppState(backStack = navViewModel.getBackStack())

            ThemeAnimationScope(state = appState.themeAnimationState) {
                DiaryTheme(darkTheme = appState.themeAnimationState.uiTheme.isDark()) {
                    Scaffold(
                        snackbarHost = { SnackbarHost(hostState = appState.snackBarManager.getHostState()) },
                        bottomBar = { BottomBar(appState = appState, bottomItems = bottomItems)},
                        contentWindowInsets = WindowInsets.safeDrawing,
                        modifier = Modifier.fillMaxSize()
                    ) { innerPadding ->
                        DiaryNavHost(appState = appState, innerPaddingValues = innerPadding)
                    }
                }
            }
        }
    }
}