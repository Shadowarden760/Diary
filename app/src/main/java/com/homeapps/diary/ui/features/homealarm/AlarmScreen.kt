package com.homeapps.diary.ui.features.homealarm

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.homeapps.diary.R
import com.homeapps.diary.ui.features.homealarm.components.TimePickerDialog
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmScreen(
    viewModel: AlarmViewModel = koinViewModel(),
    goBack: () -> Unit,
) {
    val currentContext = LocalContext.current
    TimePickerDialog(
        onConfirm = { state ->
            val calendar = Calendar.getInstance()
            calendar[android.icu.util.Calendar.HOUR_OF_DAY] = state.hour
            calendar[android.icu.util.Calendar.MINUTE] = state.minute
            calendar[android.icu.util.Calendar.SECOND] = 0
            calendar[android.icu.util.Calendar.MILLISECOND] = 0
            val result = viewModel.setAlarmScheduler(timeMillis = calendar.timeInMillis)
            if (result) {
                Toast.makeText(
                    currentContext,
                    currentContext.getString(R.string.alarm_text_alarm_was_set),
                    Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(currentContext,
                    currentContext.getString(R.string.alarm_text_alarm_wasn_t_set),
                    Toast.LENGTH_SHORT).show()
            }
        },
        onReset = {
            val result = viewModel.cancelAlarmScheduler()
            if (result) {
                Toast.makeText(currentContext,
                    currentContext.getString(R.string.alarm_text_alarm_was_canceled), 
                    Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(currentContext,
                    currentContext.getString(R.string.alarm_text_alarm_wasn_t_canceled),
                    Toast.LENGTH_SHORT).show()
            }
        },
        onDismiss = goBack
    )
}