package com.mobileni.taskmanager.screens.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobileni.taskmanager.ui.theme.TaskPalette
import com.mobileni.taskmanager.ui.theme.fonts

@Composable
fun FilterPriority(
    filter: String,
    isSelected: Boolean,
    onTapAction: () -> Unit,
) {
    val textColor = if (isSelected) {
        TaskPalette.white
    } else {
        TaskPalette.blackText
    }

    val backgroundColor = if (isSelected) {
        TaskPalette.darkBlue
    } else {
        TaskPalette.darkGray
    }

    Text(
        text = filter,
        fontFamily = fonts,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        color = textColor,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(8.dp)
            .clickable { onTapAction() }
    )
}

@Preview
@Composable
fun FilterPriorityPreview() {
    Column(
        modifier = Modifier
            .background(TaskPalette.gray)
            .padding(16.dp)
    ) {
        FilterPriority(
            filter = "Completada",
            false
        ) {}
    }
}