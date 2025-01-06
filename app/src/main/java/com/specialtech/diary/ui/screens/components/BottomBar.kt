package com.specialtech.diary.ui.screens.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.specialtech.diary.R
import com.specialtech.diary.ui.theme.MainDark
import com.specialtech.diary.ui.theme.MainOrange

@Composable
fun BottomBar() {
    val selectedItem = remember { mutableIntStateOf(0) }
    val navIcons = listOf(
        R.drawable.ic_home,
        R.drawable.ic_note,
        R.drawable.ic_weather,
        R.drawable.ic_money
    )
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
                    disabledTextColor = Color.Transparent
                ),
                selected = selectedItem.intValue == index,
                onClick = { selectedItem.intValue = index }
            )
        }
    }
}