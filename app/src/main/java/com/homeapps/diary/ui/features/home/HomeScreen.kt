package com.homeapps.diary.ui.features.home

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.homeapps.diary.BuildConfig
import com.homeapps.diary.R
import com.homeapps.diary.ui.features.home.components.DropDownLanguageMenu
import com.homeapps.diary.ui.features.home.components.ThemeSwitcher
import com.homeapps.diary.ui.features.home.components.featherIcon
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    goToAlarmScreen: () -> Unit,
    innerPadding: PaddingValues,
) {
    val darkTheme = viewModel.darkTheme.collectAsStateWithLifecycle(initialValue = false)
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

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(innerPadding)
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = goToAlarmScreen,
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