package com.homeapps.diary.ui.features.home

import android.icu.util.Calendar
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.homeapps.diary.BuildConfig
import com.homeapps.diary.R
import com.homeapps.diary.ui.features.home.components.DropDownLanguageMenu
import com.homeapps.diary.ui.features.home.components.ThemeSwitcher
import com.homeapps.diary.ui.features.home.components.TimePickerDialog
import com.homeapps.diary.ui.features.home.components.featherIcon
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val darkTheme = viewModel.darkTheme.collectAsState(initial = false)
    val currentContext = LocalContext.current
    val showTimePicker = remember { mutableStateOf(false) }
    val hasNotificationPermission = remember { mutableStateOf(viewModel.hasNotificationPermission()) }
    val notificationsPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { permission ->
        hasNotificationPermission.value = permission
    }

    LaunchedEffect(Unit) {
        if (!hasNotificationPermission.value) {
            viewModel.requestNotificationPermission(notificationsPermissionLauncher)
        }
    }

    if (showTimePicker.value) {
        TimePickerDialog(
            onConfirm = { state ->
                val calendar = Calendar.getInstance()
                calendar[Calendar.HOUR_OF_DAY] = state.hour
                calendar[Calendar.MINUTE] = state.minute
                calendar[Calendar.SECOND] = 0
                calendar[Calendar.MILLISECOND] = 0
                val result = viewModel.setAlarmScheduler(timeMillis = calendar.timeInMillis)
                if (result) {
                    Toast.makeText(currentContext, "Alarm was set", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(currentContext, "Alarm wasn't set", Toast.LENGTH_SHORT).show()
                }
                showTimePicker.value = false
            },
            onReset = {
                val result = viewModel.cancelAlarmScheduler()
                if (result) {
                    Toast.makeText(currentContext, "Alarm was canceled", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(currentContext, "Alarm wasn't canceled", Toast.LENGTH_SHORT).show()
                }
            },
            onDismiss = {
                showTimePicker.value = false
            }
        )
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, end = 24.dp)
            ) {
                IconButton(
                    onClick = { showTimePicker.value = true },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_timer),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(35.dp)
                    )
                }
                DropDownLanguageMenu(
                    onItemClick = { selectedLanguage ->
                        viewModel.changeLanguage(newLanguage = selectedLanguage)
                    }
                )
                ThemeSwitcher(
                    darkTheme = darkTheme.value,
                    onClick = { viewModel.setDarkTheme(darkTheme = !darkTheme.value) }
                )
            }
            Spacer(modifier = Modifier.weight(0.5f))
            Image(
                imageVector = featherIcon,
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                modifier = Modifier.size(200.dp)
            )
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${stringResource(R.string.home_text_version)} ${BuildConfig.VERSION_NAME}(${BuildConfig.VERSION_CODE})",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = stringResource(R.string.home_text_creator),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )
        }
    }
}