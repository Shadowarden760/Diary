package com.homeapps.diary.ui.features.weather.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.homeapps.diary.R

@Composable
fun WeatherError(
    isVisible: Boolean,
    errorMessage: String?,
    tryAgain: () -> Unit,
    goHome: () -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = errorMessage ?: stringResource(R.string.weather_text_unknown_error),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 24.dp),
            )
            Button(
                onClick = tryAgain,
                shape = MaterialTheme.shapes.medium,
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 10.dp, pressedElevation = 5.dp, focusedElevation = 10.dp,
                    hoveredElevation = 10.dp, disabledElevation = 10.dp
                ),
                modifier = Modifier
                    .width(200.dp)
                    .padding(bottom = 24.dp)
            ) {
                Text(
                    text = stringResource(R.string.weather_button_try_again),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = goHome,
                shape = MaterialTheme.shapes.medium,
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 10.dp, pressedElevation = 5.dp, focusedElevation = 10.dp,
                    hoveredElevation = 10.dp, disabledElevation = 10.dp
                ),
                modifier = Modifier
                    .width(200.dp)
            ) {
                Text(
                    text = stringResource(R.string.weather_button_return),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
private fun WeatherErrorPreview(){
    WeatherError(
        isVisible = true,
        errorMessage = "Some error",
        tryAgain = {},
        goHome = {}
    )
}