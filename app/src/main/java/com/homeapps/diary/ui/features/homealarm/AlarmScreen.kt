package com.homeapps.diary.ui.features.homealarm

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.homeapps.diary.R
import com.homeapps.diary.ui.features.homealarm.components.AlarmListItem
import com.homeapps.diary.ui.features.homealarm.components.TimePickerDialog
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmScreen(
    viewModel: AlarmViewModel = koinViewModel(),
    goBack: () -> Unit,
    innerPadding: PaddingValues,
) {
    val currentContext = LocalContext.current
    val state = viewModel.state.collectAsStateWithLifecycle()
    val alarms = viewModel.alarms.collectAsStateWithLifecycle(listOf())

    when(state.value) {
        is AlarmViewModel.AlarmScreenState.AddNewAlarm -> {
            val message = if ((state.value as AlarmViewModel.AlarmScreenState.AddNewAlarm).status)
                stringResource(R.string.alarm_text_alarm_was_set)
            else
                stringResource(R.string.alarm_text_alarm_wasn_t_set)
            Toast.makeText(currentContext, message, Toast.LENGTH_SHORT).show()
            viewModel.resetState()
        }
        is AlarmViewModel.AlarmScreenState.RemoveAlarm -> {
            val message = if ((state.value as AlarmViewModel.AlarmScreenState.RemoveAlarm).status)
                stringResource(R.string.alarm_text_alarm_was_canceled)
            else
                stringResource(R.string.alarm_text_alarm_wasn_t_canceled)
            Toast.makeText(currentContext, message, Toast.LENGTH_SHORT).show()
            viewModel.resetState()
        }
        is AlarmViewModel.AlarmScreenState.RemoveAllAlarms -> {
            val message = if ((state.value as AlarmViewModel.AlarmScreenState.RemoveAllAlarms).status)
                stringResource(R.string.alarm_text_alarms_were_canceled)
            else
                stringResource(R.string.alarm_text_alarms_weren_t_canceled)
            Toast.makeText(currentContext, message, Toast.LENGTH_SHORT).show()
            viewModel.resetState()
        }
        is AlarmViewModel.AlarmScreenState.Default -> { /* do nothing */ }
    }

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(150.dp),
        verticalItemSpacing = 16.dp,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = innerPadding,
        modifier = Modifier.fillMaxSize().padding(start = 24.dp, end = 24.dp, bottom = 16.dp)
    ) {
        item(span = StaggeredGridItemSpan.FullLine) {
            TimePickerDialog(
                onConfirm = { state ->
                    val calendar = Calendar.getInstance()
                    calendar[android.icu.util.Calendar.HOUR_OF_DAY] = state.hour
                    calendar[android.icu.util.Calendar.MINUTE] = state.minute
                    calendar[android.icu.util.Calendar.SECOND] = 0
                    calendar[android.icu.util.Calendar.MILLISECOND] = 0
                    viewModel.addNewAlarm(timeMillis = calendar.timeInMillis)
                },
                onReset = { viewModel.removeAllAlarms() },
                onDismiss = goBack
            )
        }
        item(span = StaggeredGridItemSpan.FullLine) {
            AnimatedVisibility(
                visible = alarms.value.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    text = stringResource(R.string.alarm_text_your_alarms),
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
        items(alarms.value) {
            AlarmListItem(
                alarmItem = it,
                deleteAlarmItem = { alarmItem -> Long
                    viewModel.removeAlarm(alarmItem = alarmItem)
                }
            )
        }
    }
}