package com.mobileni.taskmanager.screens.task

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mobileni.domain.model.Task
import com.mobileni.taskmanager.R
import com.mobileni.taskmanager.screens.task.composables.CustomTextField
import com.mobileni.taskmanager.ui.theme.TaskPalette
import com.mobileni.taskmanager.ui.theme.fonts
import com.mobileni.domain.utils.Constants
import com.mobileni.taskmanager.utils.FutureSelectableDates
import com.mobileni.taskmanager.utils.getPriorityBackgroundColor
import com.mobileni.taskmanager.utils.getPriorityTextColor
import com.mobileni.taskmanager.utils.toDate
import com.mobileni.taskmanager.utils.toFormattedString

@Composable
fun TaskEditScreenMain(
    viewModel: TaskEditViewModel = hiltViewModel(),
    task: Task? = null,
    onTapClose: () -> Unit
) {
    val state = viewModel.uiState

    LaunchedEffect(key1 = viewModel) {
        if (task != null) {
            viewModel.onEvent(TaskEditEvent.ValidateEditTask(task))
        }
    }

    TaskEditScreen(
        task,
        isCompleted = state.isCompleted,
        onChangeName = {
            viewModel.onEvent(TaskEditEvent.TitleChange(name = it))
        }, onChangeDetail = {
            viewModel.onEvent(TaskEditEvent.DetailChange(detail = it))
        }, onChangePriority = {
            viewModel.onEvent(TaskEditEvent.PriorityChange(priority = it))
        }, onChangeDate = {
            viewModel.onEvent(TaskEditEvent.DueDateChange(date = it))
        }, onTapCreate = {
            viewModel.onEvent(TaskEditEvent.CreateTask)
            onTapClose()
        }, onTapEdit = {
            viewModel.onEvent(TaskEditEvent.EditTask(taskId = task?.id ?: ""))
            onTapClose()
        }, onTapClose
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditScreen(
    task: Task? = null,
    isCompleted: Boolean,
    onChangeName: (String) -> Unit,
    onChangeDetail: (String) -> Unit,
    onChangePriority: (Int) -> Unit,
    onChangeDate: (Long) -> Unit,
    onTapCreate: () -> Unit,
    onTapEdit: () -> Unit,
    onTapClose: () -> Unit
) {

    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = task?.dueDate,
        selectableDates = FutureSelectableDates
    )

    val textButton = if (task?.id.isNullOrEmpty()) {
        stringResource(id = R.string.create_task)
    } else {
        stringResource(id = R.string.edit_task)
    }

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
                text = textButton,
                fontFamily = fonts,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = TaskPalette.blackText,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }

        Spacer(modifier = Modifier.size(24.dp))

        CustomTextField(
            initialValue = task?.title ?: "",
            maxLength = 30,
            title = stringResource(id = R.string.name_task),
            placeholder = stringResource(id = R.string.name),
            onChange = onChangeName
        )

        Spacer(modifier = Modifier.size(16.dp))

        CustomTextField(
            initialValue = task?.detail ?: "",
            title = stringResource(id = R.string.detail_task),
            placeholder = stringResource(id = R.string.detail),
            onChange = onChangeDetail
        )

        Spacer(modifier = Modifier.size(18.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = stringResource(id = R.string.priority),
                fontFamily = fonts,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = TaskPalette.darkBlue,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            var selectedPriority by remember { mutableIntStateOf(task?.priority ?: 0) }

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Constants.TASK_PRIORITIES.forEachIndexed { index, priority ->
                    item {
                        val textColor = if (selectedPriority == index) {
                            TaskPalette.white
                        } else {
                            index.getPriorityTextColor()
                        }

                        val backgroundColor = if (selectedPriority == index) {
                            TaskPalette.blackText
                        } else {
                            index.getPriorityBackgroundColor()
                        }

                        Text(
                            text = priority,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                            color = textColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .background(
                                    color = backgroundColor,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(top = 4.dp, bottom = 4.dp, start = 10.dp, end = 10.dp)
                                .clickable {
                                    selectedPriority = index
                                    onChangePriority(index)
                                }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.size(18.dp))


        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.due_date),
                fontFamily = fonts,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = TaskPalette.darkBlue,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.clickable { showDatePicker = true }
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_calendar),
                    contentDescription = "",
                    modifier = Modifier
                        .background(
                            color = TaskPalette.blue.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(6.dp),
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(color = TaskPalette.blue)
                )

                Text(
                    text = datePickerState.selectedDateMillis?.toDate()?.toFormattedString()
                        ?: "---",
                    fontFamily = fonts,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = TaskPalette.darkBlue,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Spacer(modifier = Modifier.size(24.dp))



        FilledTonalButton(
            enabled = isCompleted,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonColors(
                containerColor = TaskPalette.blue,
                contentColor = TaskPalette.white,
                disabledContainerColor = TaskPalette.darkGray,
                disabledContentColor = TaskPalette.grayText
            ),
            shape = RoundedCornerShape(8.dp), onClick = {
                if (task?.id.isNullOrEmpty()) {
                    onTapCreate()
                } else {
                    onTapEdit()
                }
            }) {
            Text(
                text = textButton,
                fontFamily = fonts,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                maxLines = 1,
            )
        }

        Spacer(modifier = Modifier.size(100.dp))
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { onChangeDate(it) }
                    showDatePicker = false
                }) {
                    Text(stringResource(id = R.string.ok))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(stringResource(id = R.string.cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}


@Preview
@Composable
fun TaskEditScreenPreview() {
    TaskEditScreen(
        isCompleted = false,
        onChangeName = {},
        onChangeDetail = {},
        onChangePriority = {},
        onChangeDate = {},
        onTapCreate = {},
        onTapEdit = {},
        onTapClose = {})
}