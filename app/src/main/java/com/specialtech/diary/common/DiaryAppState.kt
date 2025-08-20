package com.specialtech.diary.common

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope


class DiaryAppState(
    val scope: CoroutineScope,
    val navController: NavHostController,
    val snackbarHostState: SnackbarHostState
)

@Composable
fun rememberAppState(
    scope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) = remember(scope, navController, snackbarHostState) {
    DiaryAppState(scope, navController, snackbarHostState)
}