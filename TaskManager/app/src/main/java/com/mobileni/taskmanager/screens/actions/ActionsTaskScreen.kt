package com.mobileni.taskmanager.screens.actions

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobileni.taskmanager.R
import com.mobileni.taskmanager.ui.theme.TaskPalette
import com.mobileni.taskmanager.ui.theme.fonts
import com.mobileni.domain.utils.Constants.TASK_ACTIONS

@Composable
fun ActionsTaskScreen(
    isCompleted: Boolean,
    onTapAction: (String) -> Unit,
    onTapClose: () -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(TaskPalette.gray)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(R.drawable.ic_close),
                contentDescription = "",
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(18.dp)
                    .align(Alignment.TopStart)
                    .clickable { onTapClose() },
                contentScale = ContentScale.Fit,
            )

            Text(
                text = "Opciones de Tarea",
                fontFamily = fonts,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = TaskPalette.blackText,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }

        Spacer(modifier = Modifier.size(24.dp))

        val icons = arrayListOf(Icons.Filled.CheckCircle, Icons.Filled.Edit, Icons.Filled.Delete)
        val colors = arrayListOf(TaskPalette.green, TaskPalette.orange, TaskPalette.red)

        LazyColumn(Modifier.fillMaxWidth()) {
            itemsIndexed(TASK_ACTIONS) { index, option ->
                if (index == 2 || !isCompleted) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onTapAction(option) }
                    ) {
                        Icon(
                            imageVector = icons[index],
                            contentDescription = "action",
                            tint = colors[index]
                        )

                        Text(
                            text = option,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = TaskPalette.blackText,
                        )
                    }

                    HorizontalDivider(
                        color = TaskPalette.grayText.copy(alpha = 0.5f),
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.size(4.dp))

    }
}

@Preview
@Composable
fun ActionsTaskScreenPreview() {
    ActionsTaskScreen(isCompleted = false, onTapAction = {}) {}
}