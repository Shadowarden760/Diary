package com.homeapps.diary.ui.features.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        modifier = Modifier.fillMaxSize()
    ) {
        TimePicker(state = timePickerState)
        Button(
            onClick = onDismiss,
            modifier = Modifier.width(250.dp)
        ) {
            Text("Dismiss picker")
        }
        Button(
            onClick = onReset,
            modifier = Modifier.width(250.dp)
        ) {
            Text("Reset alarm")
        }
        Button(
            onClick = { onConfirm(timePickerState) },
            modifier = Modifier.width(250.dp)
        ) {
            Text("Confirm selection")
        }
    }
}