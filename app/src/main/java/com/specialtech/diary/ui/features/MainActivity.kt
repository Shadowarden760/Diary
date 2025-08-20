package com.specialtech.diary.ui.features

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.specialtech.diary.common.rememberAppState
import com.specialtech.diary.data.datasources.settings.AppDataStore
import com.specialtech.diary.ui.features.components.BottomBar
import com.specialtech.diary.ui.features.components.DiaryNavHost
import com.specialtech.diary.ui.theme.DiaryTheme
import org.koin.android.ext.android.inject


class MainActivity: ComponentActivity() {
    val settings: AppDataStore by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val appState = rememberAppState()
            val darkTheme = settings.darkTheme.collectAsState(initial = false)

            DiaryTheme(darkTheme = darkTheme.value) {
                Scaffold(
                    snackbarHost = { SnackbarHost(hostState = appState.snackbarHostState) },
                    bottomBar = { BottomBar(appState.navController) },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                        DiaryNavHost(appState)
                    }
                }
            }
        }
    }
}
