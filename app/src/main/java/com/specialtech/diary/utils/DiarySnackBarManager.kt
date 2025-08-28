package com.specialtech.diary.utils

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DiarySnackBarManager(
    private val coroutineScope: CoroutineScope,
    private val snackbarHostState: SnackbarHostState
) {

    fun getHostState() = snackbarHostState

    fun showSnackBar(
        message: String,
        actionLabel: String?,
        action: () -> Unit,
    ) = coroutineScope.launch {
        val snackbarResult = snackbarHostState.showSnackbar(
            message = message,
            actionLabel = actionLabel,
            withDismissAction = true,
            duration = SnackbarDuration.Short
        )
        when (snackbarResult) {
            SnackbarResult.ActionPerformed -> { action() }
            SnackbarResult.Dismissed -> { /* do nothing */ }
        }
    }
}