package com.specialtech.diary.common

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.specialtech.diary.utils.DiarySnackBarManager
import kotlinx.coroutines.CoroutineScope

class DiaryAppState(
    val navController: NavHostController,
    val snackBarManager: DiarySnackBarManager
)

@Composable
fun rememberAppState(
    scope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) = remember(scope, navController, snackbarHostState) {
    DiaryAppState(
        navController = navController,
        snackBarManager = DiarySnackBarManager(scope, snackbarHostState)
    )
}