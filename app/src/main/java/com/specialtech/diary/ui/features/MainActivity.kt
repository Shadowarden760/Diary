package com.specialtech.diary.ui.features

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.specialtech.diary.common.rememberAppState
import com.specialtech.diary.ui.features.components.BottomBar
import com.specialtech.diary.ui.features.components.DiaryNavHost
import com.specialtech.diary.ui.theme.DiaryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appState = rememberAppState()
            DiaryTheme {
                Scaffold(
                    bottomBar = { BottomBar(appState.navController) },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Box(modifier = Modifier.fillMaxSize()) {
                        DiaryNavHost(appState, innerPadding)
                    }
                }
            }
        }
    }
}
