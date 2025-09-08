package com.homeapps.diary.ui.features.homealarm

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.homeapps.diary.ui.features.homealarm.components.TimePickerDialog
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmScreen(
    viewModel: AlarmViewModel = koinViewModel(),
    goBack: () -> Unit,
) {
    val alarms = viewModel.alarms.collectAsStateWithLifecycle(listOf())

    TimePickerDialog(
        onConfirm = { state ->
            val calendar = Calendar.getInstance()
            calendar[android.icu.util.Calendar.HOUR_OF_DAY] = state.hour
            calendar[android.icu.util.Calendar.MINUTE] = state.minute
            calendar[android.icu.util.Calendar.SECOND] = 0
            calendar[android.icu.util.Calendar.MILLISECOND] = 0
            viewModel.addNewAlarm(timeMillis = calendar.timeInMillis)
        },
        onReset = {
            viewModel.removeAllAlarms()
        },
        onDismiss = goBack
    )
}