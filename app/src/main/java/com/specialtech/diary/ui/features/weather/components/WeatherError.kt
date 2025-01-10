package com.specialtech.diary.ui.features.weather.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.specialtech.diary.ui.features.weather.WeatherViewModel
import com.specialtech.diary.ui.theme.MainDark
import com.specialtech.diary.ui.theme.MainOrange

@Composable
fun WeatherError(
    isVisible: Boolean,
    forecastResult: WeatherViewModel.ForecastResult,
    tryAgain: () -> Unit,
    goHome: () -> Unit
) {
    val errorMessage = when(forecastResult) {
        is WeatherViewModel.ForecastResult.Failure -> { forecastResult.message }
        else -> { "Something went wrong..." }
    }
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = errorMessage,
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 24.dp),
                textAlign = TextAlign.Center
            )
            Button(
                onClick = tryAgain,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonColors(containerColor = MainDark, contentColor = MainOrange,
                    disabledContentColor = Color.Transparent, disabledContainerColor = Color.Transparent),
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 10.dp, pressedElevation = 5.dp, focusedElevation = 10.dp,
                    hoveredElevation = 10.dp, disabledElevation = 10.dp
                ),
                modifier = Modifier
                    .width(200.dp)
                    .padding(bottom = 24.dp)
            ) {
                Text(
                    text = "Try Again",
                    fontSize = 16.sp
                )
            }

            Button(
                onClick = goHome,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonColors(containerColor = MainDark, contentColor = MainOrange,
                    disabledContentColor = Color.Transparent, disabledContainerColor = Color.Transparent),
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 10.dp, pressedElevation = 5.dp, focusedElevation = 10.dp,
                    hoveredElevation = 10.dp, disabledElevation = 10.dp
                ),
                modifier = Modifier
                    .width(200.dp)
            ) {
                Text(
                    text = "Return",
                    fontSize = 16.sp
                )
            }
        }
    }
}