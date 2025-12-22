package com.universall.watertracker.main.features.water_tracker.ui.water_view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke


fun DrawScope.drawWave(
    startX: Float,
    startY: Float,
    control1: Offset,
    control2: Offset,
    end: Offset,
    fillBrush: Brush
) {
    val path = Path().apply {
        moveTo(startX, startY)
        cubicTo(control1.x, control1.y, control2.x, control2.y, end.x, end.y)
        lineTo(size.width, size.height)
        lineTo(0f, size.height)
        close()
    }
    drawPath(path = path, brush = fillBrush)
}


@Composable
fun MainScreenBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    Box(modifier = modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // --- Общий Градиент Фона ---
            drawRect(
                brush = Brush.verticalGradient(
                    0.0f to Color(0xFF5BA8F5),
                    0.5f to Color(0xFF3886E3),
                    1.0f to Color(0xFF2558D8)
                )
            )

            // --- Верхняя волна ---
            val topWavePath = Path().apply {
                moveTo(0f, height * 0.38f)
                cubicTo(
                    width * 0.35f, height * 0.32f,
                    width * 0.65f, height * 0.55f,
                    width, height * 0.48f
                )
                lineTo(width, height)
                lineTo(0f, height)
                close()
            }

            // Заливка верхней волны (градиент)
            drawPath(
                path = topWavePath,
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0x50FFFFFF), Color(0x10FFFFFF)),
                    startY = height * 0.35f,
                    endY = height * 0.7f
                )
            )

            // Тонкая светлая линяя по верхнему краю верхней волны
            val borderPath = Path().apply {
                moveTo(0f, height * 0.38f)
                cubicTo(
                    width * 0.35f, height * 0.32f,
                    width * 0.65f, height * 0.55f,
                    width, height * 0.48f
                )
            }

            // Отрисовка линии
            drawPath(
                path = borderPath,
                color = Color(0x80FFFFFF),
                style = Stroke(width = 4f)
            )

            // Вторая волна
            drawWave(
                startX = 0f,
                startY = height * 0.53f,
                control1 = Offset(width * 0.4f, height * 0.50f),
                control2 = Offset(width * 0.75f, height * 0.65f),
                end = Offset(width, height * 0.5f),
                fillBrush = Brush.verticalGradient(
                    colors = listOf(Color(0x33FFFFFF), Color(0x00FFFFFF)),
                    startY = height * 0.4f,
                    endY = height * 0.8f
                )
            )

            // Третья волна
            drawWave(
                startX = 0f,
                startY = height * 0.65f, // Начало еще ниже (65% высоты)
                control1 = Offset(width * 0.3f, height * 0.70f), // Изгиб
                control2 = Offset(width * 0.75f, height * 0.55f), // Изгиб
                end = Offset(width, height * 0.60f), // Конец на 60% высоты
                fillBrush = Brush.verticalGradient(
                    colors = listOf(Color(0x22FFFFFF), Color(0x00FFFFFF)),
                    startY = height * 0.70f,
                    endY = height
                )
            )

            // Четвёртая волна
            drawWave(
                startX = 0f,
                startY = height * 0.75f,
                control1 = Offset(width * 0.3f, height * 0.67f),
                control2 = Offset(width * 0.7f, height * 0.90f),
                end = Offset(width, height * 0.8f),
                fillBrush = Brush.verticalGradient(
                    colors = listOf(Color(0x22FFFFFF), Color(0x00FFFFFF)),
                    startY = height * 0.70f,
                    endY = height
                )
            )
        }

        content()
    }
}
