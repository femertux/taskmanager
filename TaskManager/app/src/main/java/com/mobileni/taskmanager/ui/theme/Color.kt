package com.mobileni.taskmanager.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

object TaskPalette {
    val blue = Color(0xFF1E8EBA)
    val darkBlue = Color(0xFF1F2A38)
    val white = Color(0xFFFFFFFF)
    val red = Color(0xFFFF8E8D)
    val orange = Color(0xFFF6BA50)
    val gray = Color(0xFFF3F3F3)
    val darkGray = Color(0xFFEEEFF2)
    val green = Color(0xFF1BC473)

    val blackText = Color(0xFF111927)
    val grayText = Color(0xFFACB0B7)

}

// Light theme
val TaskLightColorsPalette = lightColorScheme(
    primary = TaskPalette.blue,
    secondary = TaskPalette.darkBlue,
    background = TaskPalette.gray,
    surfaceContainer = TaskPalette.white
)

// Dark theme
val TaskDarkColorsPalette = darkColorScheme(
    primary = TaskPalette.blue,
    secondary = TaskPalette.darkBlue,
    background = TaskPalette.gray,
    surfaceContainer = TaskPalette.white
)