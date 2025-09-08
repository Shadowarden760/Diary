package com.homeapps.diary.ui.features.homealarm.components

import android.icu.util.Calendar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.homeapps.diary.R
import com.homeapps.diary.domain.models.alarm.AlarmItem

@Composable
fun AlarmListItem(
    alarmItem: AlarmItem,
    deleteAlarmItem: (AlarmItem) -> Unit
) {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = alarmItem.alarmTimeMillis
    Card(modifier = Modifier.fillMaxWidth()) {
        IconButton(
            onClick = { deleteAlarmItem(alarmItem) },
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_cancel),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(30.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_timer),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(30.dp)

            )
            Text(
                text = "${calendar[Calendar.HOUR_OF_DAY]}:${calendar[Calendar.MINUTE]}",
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview
@Composable
fun AlarmListItemPreview() {
    AlarmListItem(AlarmItem(alarmId = 1L, alarmTimeMillis = 239821839128L), {})
}