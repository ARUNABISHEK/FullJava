package com.example.comp

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@SuppressLint("RememberReturnType")
@Composable
fun CustomComponent(
    canvasSize: Dp = 300.dp,
    indicatorValue: Int = 0,
    maxIndicatorValue: Int = 100,
    backgroundIndicatorColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
    backgroundIndicatorStrokeWidth : Float = 100f,
    foregroundIndicatorColor: Color = MaterialTheme.colorScheme.primary,
    foregroundIndicatorStrokeWidth : Float = 100f
) {

    val animatedIndicatedValue = remember { Animatable(initialValue = 0f) }

    LaunchedEffect(key1 = indicatorValue) {
        //Recompose block
        animatedIndicatedValue.animateTo(indicatorValue.toFloat())
    }

    val percentage = (animatedIndicatedValue.value / maxIndicatorValue) * 100

    val sweepAngle by animateFloatAsState(
        targetValue = (2.4 * percentage).toFloat(),
        animationSpec = tween(1000)
    )
    Column(
        Modifier
            .size(canvasSize)   //Size of total space
            .drawBehind {
                val componentSize = size / 1.25f    //size of using space

                backgroundInterrogate(
                    componentSize = componentSize,
                    indicatorColor = backgroundIndicatorColor,
                    strokeWidth = backgroundIndicatorStrokeWidth
                )

                foregroundInterrogate(
                    sweepAngle = sweepAngle,
                    componentSize = componentSize,
                    indicatorColor = foregroundIndicatorColor,
                    strokeWidth = foregroundIndicatorStrokeWidth
                )
            }
    ) {

    }
}

fun DrawScope.backgroundInterrogate(
    componentSize : Size,
    indicatorColor : Color,
    strokeWidth : Float
) {
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 150f,
        sweepAngle = 240f,
        useCenter = false,
        style = Stroke(
            width = strokeWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        )
    )
}

fun DrawScope.foregroundInterrogate(
    sweepAngle : Float,
    componentSize : Size,
    indicatorColor : Color,
    strokeWidth : Float
) {
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 150f,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(
            width = strokeWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        )
    )
}

@Composable
@Preview(showBackground = true)
private fun CustomComponentPreview() {
    CustomComponent()
}