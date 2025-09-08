package com.homeapps.diary.ui.features.homealarm.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.homeapps.diary.R
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    onConfirm: (TimePickerState) -> Unit,
    onReset: () -> Unit,
    onDismiss: () -> Unit
) {
    val currentTime = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime[Calendar.HOUR_OF_DAY],
        initialMinute = currentTime[Calendar.MINUTE],
        is24Hour = true,
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.alarm_text_set_your_alarm),
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(Modifier.weight(1f))
            IconButton(onClick = onDismiss) {
                Icon(
                    painter = painterResource(R.drawable.ic_cancel),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null,
                    modifier = Modifier.size(35.dp)
                )
            }
        }
        TimePicker(state = timePickerState)
        Button(
            onClick = onReset,
            modifier = Modifier.width(250.dp)
        ) {
            Text(stringResource(R.string.alarm_button_reset_alarms))
        }
        Button(
            onClick = { onConfirm(timePickerState) },
            modifier = Modifier.width(250.dp)
        ) {
            Text(stringResource(R.string.alarm_button_confirm_alarm))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun TimerPickerDialogPreview() {
    TimePickerDialog({}, {}, {})
}