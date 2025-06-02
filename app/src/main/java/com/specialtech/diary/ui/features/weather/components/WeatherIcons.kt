package com.specialtech.diary.ui.features.weather.components
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

object WeatherIcons {
    private var _humidity: ImageVector? = null
    private var _rain: ImageVector? = null
    private var _wind: ImageVector? = null

    val humidity: ImageVector
        get() {
            if (_humidity != null) {
                return _humidity!!
            }
            _humidity = ImageVector.Builder(
                name = "Humidity",
                defaultWidth = 16.dp,
                defaultHeight = 16.dp,
                viewportWidth = 16f,
                viewportHeight = 16f
            ).apply {
                path(
                    fill = SolidColor(Color(0xFF000000)),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(13.5f, 0f)
                    arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, 1f)
                    horizontalLineTo(15f)
                    verticalLineToRelative(2.75f)
                    horizontalLineToRelative(-0.5f)
                    arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, 1f)
                    horizontalLineToRelative(0.5f)
                    verticalLineTo(7.5f)
                    horizontalLineToRelative(-1.5f)
                    arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, 1f)
                    horizontalLineTo(15f)
                    verticalLineToRelative(2.75f)
                    horizontalLineToRelative(-0.5f)
                    arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, 1f)
                    horizontalLineToRelative(0.5f)
                    verticalLineTo(15f)
                    horizontalLineToRelative(-1.5f)
                    arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, 1f)
                    horizontalLineToRelative(2f)
                    arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0.5f, -0.5f)
                    verticalLineTo(0.5f)
                    arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.5f, -0.5f)
                    close()
                    moveTo(7f, 1.5f)
                    lineToRelative(0.364f, -0.343f)
                    arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.728f, 0f)
                    lineToRelative(-0.002f, 0.002f)
                    lineToRelative(-0.006f, 0.007f)
                    lineToRelative(-0.022f, 0.023f)
                    lineToRelative(-0.08f, 0.088f)
                    arcToRelative(29f, 29f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.274f, 1.517f)
                    curveToRelative(-0.769f, 0.983f, -1.714f, 2.325f, -2.385f, 3.727f)
                    curveTo(2.368f, 7.564f, 2f, 8.682f, 2f, 9.733f)
                    curveTo(2f, 12.614f, 4.212f, 15f, 7f, 15f)
                    reflectiveCurveToRelative(5f, -2.386f, 5f, -5.267f)
                    curveToRelative(0f, -1.05f, -0.368f, -2.169f, -0.867f, -3.212f)
                    curveToRelative(-0.671f, -1.402f, -1.616f, -2.744f, -2.385f, -3.727f)
                    arcToRelative(29f, 29f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.354f, -1.605f)
                    lineToRelative(-0.022f, -0.023f)
                    lineToRelative(-0.006f, -0.007f)
                    lineToRelative(-0.002f, -0.001f)
                    close()
                    moveToRelative(0f, 0f)
                    lineToRelative(-0.364f, -0.343f)
                    close()
                    moveToRelative(-0.016f, 0.766f)
                    lineTo(7f, 2.247f)
                    lineToRelative(0.016f, 0.019f)
                    curveToRelative(0.24f, 0.274f, 0.572f, 0.667f, 0.944f, 1.144f)
                    curveToRelative(0.611f, 0.781f, 1.32f, 1.776f, 1.901f, 2.827f)
                    horizontalLineTo(4.14f)
                    curveToRelative(0.58f, -1.051f, 1.29f, -2.046f, 1.9f, -2.827f)
                    curveToRelative(0.373f, -0.477f, 0.706f, -0.87f, 0.945f, -1.144f)
                    close()
                    moveTo(3f, 9.733f)
                    curveToRelative(0f, -0.755f, 0.244f, -1.612f, 0.638f, -2.496f)
                    horizontalLineToRelative(6.724f)
                    curveToRelative(0.395f, 0.884f, 0.638f, 1.741f, 0.638f, 2.496f)
                    curveTo(11f, 12.117f, 9.182f, 14f, 7f, 14f)
                    reflectiveCurveToRelative(-4f, -1.883f, -4f, -4.267f)
                }
            }.build()
            return _humidity!!
        }

    val rain: ImageVector
        get() {
            if (_rain != null) {
                return _rain!!
            }
            _rain = ImageVector.Builder(
                name = "Rain",
                defaultWidth = 16.dp,
                defaultHeight = 16.dp,
                viewportWidth = 16f,
                viewportHeight = 16f
            ).apply {
                path(
                    fill = SolidColor(Color(0xFF000000)),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(8f, 0f)
                    arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.5f, 0.5f)
                    verticalLineToRelative(0.514f)
                    curveTo(12.625f, 1.238f, 16f, 4.22f, 16f, 8f)
                    curveToRelative(0f, 0f, 0f, 0.5f, -0.5f, 0.5f)
                    curveToRelative(-0.149f, 0f, -0.352f, -0.145f, -0.352f, -0.145f)
                    lineToRelative(-0.004f, -0.004f)
                    lineToRelative(-0.025f, -0.023f)
                    arcToRelative(3.5f, 3.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.555f, -0.394f)
                    arcTo(3.17f, 3.17f, 0f, isMoreThanHalf = false, isPositiveArc = false, 13f, 7.5f)
                    curveToRelative(-0.638f, 0f, -1.178f, 0.213f, -1.564f, 0.434f)
                    arcToRelative(3.5f, 3.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.555f, 0.394f)
                    lineToRelative(-0.025f, 0.023f)
                    lineToRelative(-0.003f, 0.003f)
                    reflectiveCurveToRelative(-0.204f, 0.146f, -0.353f, 0.146f)
                    reflectiveCurveToRelative(-0.352f, -0.145f, -0.352f, -0.145f)
                    lineToRelative(-0.004f, -0.004f)
                    lineToRelative(-0.025f, -0.023f)
                    arcToRelative(3.5f, 3.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.555f, -0.394f)
                    arcToRelative(3.3f, 3.3f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.064f, -0.39f)
                    verticalLineTo(13.5f)
                    horizontalLineTo(8f)
                    horizontalLineToRelative(0.5f)
                    verticalLineToRelative(0.039f)
                    lineToRelative(-0.005f, 0.083f)
                    arcToRelative(3f, 3f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.298f, 1.102f)
                    arcToRelative(2.26f, 2.26f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.763f, 0.88f)
                    curveTo(7.06f, 15.851f, 6.587f, 16f, 6f, 16f)
                    reflectiveCurveToRelative(-1.061f, -0.148f, -1.434f, -0.396f)
                    arcToRelative(2.26f, 2.26f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.763f, -0.88f)
                    arcToRelative(3f, 3f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.302f, -1.185f)
                    verticalLineToRelative(-0.025f)
                    lineToRelative(-0.001f, -0.009f)
                    verticalLineToRelative(-0.003f)
                    reflectiveCurveToRelative(0f, -0.002f, 0.5f, -0.002f)
                    horizontalLineToRelative(-0.5f)
                    verticalLineTo(13f)
                    arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1f, 0f)
                    verticalLineToRelative(0.506f)
                    lineToRelative(0.003f, 0.044f)
                    arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0.195f, 0.726f)
                    curveToRelative(0.095f, 0.191f, 0.23f, 0.367f, 0.423f, 0.495f)
                    curveToRelative(0.19f, 0.127f, 0.466f, 0.229f, 0.879f, 0.229f)
                    reflectiveCurveToRelative(0.689f, -0.102f, 0.879f, -0.229f)
                    curveToRelative(0.193f, -0.128f, 0.328f, -0.304f, 0.424f, -0.495f)
                    arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0.197f, -0.77f)
                    verticalLineTo(7.544f)
                    arcToRelative(3.3f, 3.3f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.064f, 0.39f)
                    arcToRelative(3.5f, 3.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.58f, 0.417f)
                    lineToRelative(-0.004f, 0.004f)
                    reflectiveCurveTo(5.65f, 8.5f, 5.5f, 8.5f)
                    reflectiveCurveToRelative(-0.352f, -0.145f, -0.352f, -0.145f)
                    lineToRelative(-0.004f, -0.004f)
                    arcToRelative(3.5f, 3.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.58f, -0.417f)
                    arcTo(3.17f, 3.17f, 0f, isMoreThanHalf = false, isPositiveArc = false, 3f, 7.5f)
                    curveToRelative(-0.638f, 0f, -1.177f, 0.213f, -1.564f, 0.434f)
                    arcToRelative(3.5f, 3.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.58f, 0.417f)
                    lineToRelative(-0.004f, 0.004f)
                    reflectiveCurveTo(0.65f, 8.5f, 0.5f, 8.5f)
                    curveTo(0f, 8.5f, 0f, 8f, 0f, 8f)
                    curveToRelative(0f, -3.78f, 3.375f, -6.762f, 7.5f, -6.986f)
                    verticalLineTo(0.5f)
                    arcTo(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 8f, 0f)
                    moveTo(6.577f, 2.123f)
                    curveToRelative(-2.833f, 0.5f, -4.99f, 2.458f, -5.474f, 4.854f)
                    arcTo(4.1f, 4.1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 3f, 6.5f)
                    curveToRelative(0.806f, 0f, 1.48f, 0.25f, 1.962f, 0.511f)
                    arcToRelative(9.7f, 9.7f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.344f, -2.358f)
                    curveToRelative(0.242f, -0.868f, 0.64f, -1.765f, 1.271f, -2.53f)
                    moveToRelative(-0.615f, 4.93f)
                    arcTo(4.16f, 4.16f, 0f, isMoreThanHalf = false, isPositiveArc = true, 8f, 6.5f)
                    arcToRelative(4.16f, 4.16f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.038f, 0.553f)
                    arcToRelative(8.7f, 8.7f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.307f, -2.13f)
                    curveTo(9.434f, 3.858f, 8.898f, 2.83f, 8f, 2.117f)
                    curveToRelative(-0.898f, 0.712f, -1.434f, 1.74f, -1.731f, 2.804f)
                    arcToRelative(8.7f, 8.7f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.307f, 2.131f)
                    close()
                    moveToRelative(3.46f, -4.93f)
                    curveToRelative(0.631f, 0.765f, 1.03f, 1.662f, 1.272f, 2.53f)
                    curveToRelative(0.233f, 0.833f, 0.328f, 1.66f, 0.344f, 2.358f)
                    arcTo(4.14f, 4.14f, 0f, isMoreThanHalf = false, isPositiveArc = true, 13f, 6.5f)
                    curveToRelative(0.77f, 0f, 1.42f, 0.23f, 1.897f, 0.477f)
                    curveToRelative(-0.484f, -2.396f, -2.641f, -4.355f, -5.474f, -4.854f)
                    close()
                }
            }.build()
            return _rain!!
        }

    val wind: ImageVector
        get() {
            if (_wind != null) {
                return _wind!!
            }
            _wind = ImageVector.Builder(
                name = "Wind",
                defaultWidth = 16.dp,
                defaultHeight = 16.dp,
                viewportWidth = 16f,
                viewportHeight = 16f
            ).apply {
                path(
                    fill = SolidColor(Color(0xFF000000)),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(12.5f, 2f)
                    arcTo(2.5f, 2.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 10f, 4.5f)
                    arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1f, 0f)
                    arcTo(3.5f, 3.5f, 0f, isMoreThanHalf = true, isPositiveArc = true, 12.5f, 8f)
                    horizontalLineTo(0.5f)
                    arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, -1f)
                    horizontalLineToRelative(12f)
                    arcToRelative(2.5f, 2.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, -5f)
                    moveToRelative(-7f, 1f)
                    arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1f, 1f)
                    arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1f, 0f)
                    arcToRelative(2f, 2f, 0f, isMoreThanHalf = true, isPositiveArc = true, 2f, 2f)
                    horizontalLineToRelative(-5f)
                    arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, -1f)
                    horizontalLineToRelative(5f)
                    arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, -2f)
                    moveTo(0f, 9.5f)
                    arcTo(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.5f, 9f)
                    horizontalLineToRelative(10.042f)
                    arcToRelative(3f, 3f, 0f, isMoreThanHalf = true, isPositiveArc = true, -3f, 3f)
                    arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1f, 0f)
                    arcToRelative(2f, 2f, 0f, isMoreThanHalf = true, isPositiveArc = false, 2f, -2f)
                    horizontalLineTo(0.5f)
                    arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.5f, -0.5f)
                }
            }.build()
            return _wind!!
        }

}