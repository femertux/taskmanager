package com.mobileni.taskmanager.screens.home.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobileni.taskmanager.R
import com.mobileni.taskmanager.ui.theme.TaskPalette
import com.mobileni.taskmanager.ui.theme.fonts
import com.mobileni.taskmanager.utils.getPriorityBackgroundColor
import com.mobileni.taskmanager.utils.getPriorityLabel
import com.mobileni.taskmanager.utils.getPriorityTextColor
import com.mobileni.taskmanager.utils.getStatusLabel
import com.mobileni.taskmanager.utils.getStatusTextColor

@Composable
fun TaskItem(
    title: String,
    detail: String,
    dueDate: String,
    priority: Int,
    isCompleted: Boolean,
    onTapAction: () -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = TaskPalette.white,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 128.dp),
        elevation = CardDefaults.elevatedCardElevation(0.dp)
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(12.dp),
        ) {
            Text(
                text = isCompleted.getStatusLabel(),
                fontFamily = fonts,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                color = isCompleted.getStatusTextColor(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.TopStart)
            )

            Image(
                painter = painterResource(R.drawable.ic_dots),
                contentDescription = "",
                modifier = Modifier
                    .size(18.dp)
                    .align(Alignment.TopEnd)
                    .clickable { onTapAction() },
                contentScale = ContentScale.Fit,
            )

            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
            ) {
                Text(
                    text = title,
                    fontFamily = fonts,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = TaskPalette.blackText,
                )

                Text(
                    text = detail,
                    fontFamily = fonts,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = TaskPalette.grayText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.ic_calendar),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .size(10.dp),
                        contentScale = ContentScale.Fit,
                    )

                    Text(
                        text = dueDate,
                        fontFamily = fonts,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = TaskPalette.blackText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Text(
                    text = priority.getPriorityLabel(),
                    fontFamily = fonts,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = priority.getPriorityTextColor(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .background(
                            color = priority.getPriorityBackgroundColor(),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(4.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun TaskItemPreview() {
    Column(
        modifier = Modifier
            .background(TaskPalette.gray)
            .padding(16.dp)
    ) {
        TaskItem(
            title = "Song app UI design",
            detail = "Create a home screen",
            dueDate = "Sabado 11:00 pm",
            priority = 1,
            isCompleted = true
        ) {}
    }
}