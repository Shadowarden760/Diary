package com.specialtech.diary.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class DiaryAppState(
    val navController: NavHostController,
)

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
) = remember(navController) {
    DiaryAppState(navController)
}