package com.mobileni.taskmanager.screens.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobileni.taskmanager.R
import com.mobileni.taskmanager.ui.theme.TaskPalette
import com.mobileni.taskmanager.ui.theme.fonts

@Composable
fun EmptyTask(
    onTapAction: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        LottieEmptyAnimation()

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = stringResource(id = R.string.empty_task),
            fontFamily = fonts,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = TaskPalette.darkBlue,
            maxLines = 1,
        )

        OutlinedButton(
            border = BorderStroke(1.dp, TaskPalette.darkBlue),
            shape = RoundedCornerShape(8.dp),
            onClick = { onTapAction() }) {
            Text(
                text = stringResource(id = R.string.create_task),
                fontFamily = fonts,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                color = TaskPalette.darkBlue,
                maxLines = 1,
            )
        }


    }
}

@Preview
@Composable
fun EmptyTaskPreview() {
    Column(
        modifier = Modifier
            .background(TaskPalette.gray)
            .padding(16.dp)
    ) {
        EmptyTask {}
    }
}