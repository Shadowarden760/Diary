package com.specialtech.diary.ui.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.specialtech.diary.BuildConfig
import com.specialtech.diary.R
import com.specialtech.diary.ui.features.home.components.featherIcon
import org.koin.androidx.compose.koinViewModel


@Preview(showSystemUi = true)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.weight(0.5f))
        Image(
            imageVector = featherIcon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .size(200.dp)
                .padding(top = 10.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "${stringResource(R.string.home_text_version)} ${BuildConfig.VERSION_NAME}(${BuildConfig.VERSION_CODE})",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )
    }
}