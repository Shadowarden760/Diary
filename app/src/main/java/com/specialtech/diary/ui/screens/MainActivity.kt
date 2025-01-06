package com.specialtech.diary.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.specialtech.diary.ui.screens.components.BottomBar
import com.specialtech.diary.ui.screens.home.HomeScreen
import com.specialtech.diary.ui.screens.weather.WeatherScreen
import com.specialtech.diary.ui.theme.DiaryTheme
import com.specialtech.diary.ui.theme.MainDark
import com.specialtech.diary.ui.theme.MainOrange

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DiaryTheme {
                Scaffold(
                    bottomBar = { BottomBar() },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(brush = Brush.horizontalGradient(listOf(MainDark, MainOrange)))
                    ) {
                        WeatherScreen(innerPaddingValues = innerPadding)
                        //HomeScreen(innerPadding)
                    }
                }
            }
        }
    }
}
