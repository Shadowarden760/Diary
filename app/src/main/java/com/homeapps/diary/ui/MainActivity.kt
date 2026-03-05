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
import com.homeapps.diary.common.rememberAppState
import com.homeapps.diary.ui.features.components.DiaryNavHost
import com.homeapps.diary.ui.features.components.navigationbar.BottomBar
import com.homeapps.diary.ui.theme.DiaryTheme
import io.github.themeanimator.ThemeAnimationScope
import io.github.themeanimator.theme.isDark

class MainActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val appState = rememberAppState()

            ThemeAnimationScope(state = appState.themeAnimationState) {

                DiaryTheme(darkTheme = appState.themeAnimationState.uiTheme.isDark()) {
                    Scaffold(
                        snackbarHost = { SnackbarHost(hostState = appState.snackBarManager.getHostState()) },
                        bottomBar = { BottomBar(navHostController = appState.navController) },
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