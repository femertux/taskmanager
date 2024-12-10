package com.mobileni.taskmanager.utils

import androidx.compose.ui.graphics.Color
import com.mobileni.taskmanager.ui.theme.TaskPalette
import com.mobileni.domain.utils.Constants.TASK_PRIORITIES
import com.mobileni.domain.utils.Constants.TASK_STATUS
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun Int.getPriorityLabel(): String {
    return TASK_PRIORITIES[this]
}

fun Boolean.getStatusLabel(): String {
    return if (this) {
        TASK_STATUS[1]
    } else {
        TASK_STATUS[0]
    }
}

fun Int.getPriorityTextColor(): Color {
    return when (this) {
        0 -> TaskPalette.blue
        1 -> TaskPalette.orange
        else -> {
            TaskPalette.red
        }
    }
}

fun Int.getPriorityBackgroundColor(): Color {
    return when (this) {
        0 -> TaskPalette.blue.copy(alpha = 0.2f)
        1 -> TaskPalette.orange.copy(alpha = 0.2f)
        else -> {
            TaskPalette.red.copy(alpha = 0.2f)
        }
    }
}

fun Boolean.getStatusTextColor(): Color {
    return if (this) {
        TaskPalette.green
    } else {
        TaskPalette.blue
    }
}

fun Long.toDate(): Date {
    return Date(this)
}

fun Date.toFormattedString(): String {
    val format = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
    format.timeZone = TimeZone.getDefault()
    return format.format(this)
}

fun Date.isValidDate(): Boolean {
    return this.after(Date())
}