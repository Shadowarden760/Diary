package com.homeapps.diary.ui.features.weather.components

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.homeapps.diary.BuildConfig
import com.homeapps.diary.R
import io.github.dellisd.spatialk.geojson.Position
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.maplibre.compose.camera.CameraPosition
import org.maplibre.compose.camera.rememberCameraState
import org.maplibre.compose.map.MapOptions
import org.maplibre.compose.map.MaplibreMap
import org.maplibre.compose.map.OrnamentOptions
import org.maplibre.compose.material3.CompassButton
import org.maplibre.compose.material3.PointerPinButton
import org.maplibre.compose.material3.ScaleBar
import org.maplibre.compose.style.BaseStyle
import org.maplibre.compose.style.rememberStyleState
import kotlin.time.Duration.Companion.milliseconds

fun Modifier.onPointerInteractionStartEnd(
    onPointerStart: () -> Unit,
    onPointerEnd: () -> Unit,
) = pointerInput(onPointerStart, onPointerEnd) {
    awaitEachGesture {
        awaitFirstDown(requireUnconsumed = false)
        onPointerStart()
        do {
            val event = awaitPointerEvent()
        } while (event.changes.any { it.pressed })
        onPointerEnd()
    }
}

@Composable
fun DiaryMap(userPosition: Position, onMapTouch: (Boolean) -> Unit) {
    val cameraState = rememberCameraState(
        firstPosition = CameraPosition(target = userPosition, zoom = 12.0)
    )
    val styleState = rememberStyleState()
    val dpTarget = remember(userPosition, cameraState.position) {
        cameraState.projection?.screenLocationFromPosition(userPosition)
    }
    val target = dpTarget?.let {
        with(LocalDensity.current) { Offset(dpTarget.x.toPx(), dpTarget.y.toPx()) }
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            MaplibreMap(
                baseStyle = BaseStyle.Uri(
                    uri = "${BuildConfig.MAP_API_URL}/maps/streets-v2/style.json?key=${BuildConfig.MAPTILER_API_KEY}"
                ),
                styleState = styleState,
                options = MapOptions(ornamentOptions = OrnamentOptions.OnlyLogo),
                cameraState = cameraState,
                modifier = Modifier
                    .fillMaxSize()
                    .onPointerInteractionStartEnd(
                        onPointerStart = { onMapTouch(true) },
                        onPointerEnd = { onMapTouch(false) }
                    )
            )
            Box(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                ScaleBar(
                    metersPerDp = cameraState.metersPerDpAtTarget,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.TopStart)
                )
                CompassButton(cameraState, modifier = Modifier.align(Alignment.TopEnd))
                PointerPinButton(
                    cameraState = cameraState,
                    targetPosition = userPosition,
                    onClick = {
                        CoroutineScope(Dispatchers.Main).launch {
                            cameraState.animateTo(
                                finalPosition = CameraPosition(target = userPosition, zoom = 12.0),
                                duration = 500.milliseconds
                            )
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_location),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                }
                IconButton(
                    onClick = {},
                    colors = IconButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                        disabledContentColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .absoluteOffset{
                            if (target != null) {
                                IntOffset(target.x.toInt(), target.y.toInt())
                            } else {
                                IntOffset(0, 0)
                            }
                        }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_location),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }

            }
        }
    }
}

@Preview
@Composable
private fun DiaryMapPreview() {
    DiaryMap(userPosition = Position(0.0, 0.0)) { }
}