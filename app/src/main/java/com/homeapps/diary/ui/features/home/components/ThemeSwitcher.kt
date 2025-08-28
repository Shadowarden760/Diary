package com.homeapps.diary.ui.features.home.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.homeapps.diary.R

@Composable
fun ThemeSwitcher(onClick: () -> Unit, darkTheme: Boolean) {
    val themeIconId = if (darkTheme) R.drawable.ic_moon else R.drawable.ic_sun
    val rotationAngle = animateFloatAsState(targetValue = if (darkTheme) 0f else 180f)

    IconButton(
        onClick =  onClick,
        modifier = Modifier.padding(start = 8.dp)
    ) {
        Icon(
            painter = painterResource(id = themeIconId),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(35.dp)
                .graphicsLayer {
                    rotationZ = rotationAngle.value
                }
        )
    }
}

@Preview
@Composable
private fun ThemeSwitcherPreview() {
    ThemeSwitcher({}, true)
}