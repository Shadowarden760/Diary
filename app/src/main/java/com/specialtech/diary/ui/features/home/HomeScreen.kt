package com.specialtech.diary.ui.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.specialtech.diary.BuildConfig
import com.specialtech.diary.R
import com.specialtech.diary.ui.features.home.components.DropDownLanguageMenu
import com.specialtech.diary.ui.features.home.components.ThemeSwitcher
import com.specialtech.diary.ui.features.home.components.featherIcon
import org.koin.androidx.compose.koinViewModel


@Preview(showSystemUi = true)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    val darkTheme = viewModel.darkTheme.collectAsState(initial = false)

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
            DropDownLanguageMenu(
                onItemClick = { selectedLanguage ->
                    viewModel.changeLanguage(newLanguage = selectedLanguage)
                }
            )
            ThemeSwitcher(
                onClick = { viewModel.setDarkTheme(darkTheme = !darkTheme.value) },
                darkTheme = darkTheme.value
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