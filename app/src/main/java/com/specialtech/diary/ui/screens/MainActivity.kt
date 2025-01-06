package com.specialtech.diary.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.specialtech.diary.R
import com.specialtech.diary.ui.screens.home.HomeScreen
import com.specialtech.diary.ui.theme.DiaryTheme
import com.specialtech.diary.ui.theme.MainDark
import com.specialtech.diary.ui.theme.MainOrange

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val selectedItem = remember { mutableIntStateOf(0) }
            val navIcons = listOf(
                R.drawable.ic_home,
                R.drawable.ic_note,
                R.drawable.ic_weather,
                R.drawable.ic_money
            )
            DiaryTheme {
                Scaffold(
                    bottomBar = {
                        NavigationBar(containerColor = MainDark) {
                            navIcons.forEachIndexed { index, item ->
                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            painter = painterResource(item),
                                            contentDescription = null
                                        )
                                    },
                                    colors = NavigationBarItemColors(
                                        selectedIconColor = MainOrange,
                                        unselectedIconColor = Color.White,
                                        selectedTextColor = Color.Transparent,
                                        unselectedTextColor = Color.Transparent,
                                        selectedIndicatorColor = Color.Transparent,
                                        disabledIconColor = MainOrange,
                                        disabledTextColor = MainDark
                                    ),
                                    selected = selectedItem.intValue == index,
                                    onClick = { selectedItem.intValue = index }
                                )
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.horizontalGradient(
                                listOf(MainDark, MainOrange)
                            )
                        )
                    ) {
                        //WeatherScreen(innerPaddingValues = innerPadding)
                        HomeScreen(innerPadding)
                    }
                }
            }
        }
    }
}

