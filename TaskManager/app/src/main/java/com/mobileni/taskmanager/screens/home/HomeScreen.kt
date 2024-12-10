package com.mobileni.taskmanager.screens.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mobileni.domain.model.Task
import com.mobileni.domain.utils.Constants
import com.mobileni.domain.utils.Constants.TASK_ACTIONS
import com.mobileni.domain.utils.Constants.TASK_STATUS_FILTERS
import com.mobileni.taskmanager.R
import com.mobileni.taskmanager.screens.actions.ActionsTaskScreen
import com.mobileni.taskmanager.screens.common.EmptyTask
import com.mobileni.taskmanager.screens.home.composables.FilterPriority
import com.mobileni.taskmanager.screens.home.composables.FilterTexField
import com.mobileni.taskmanager.screens.home.composables.TaskItem
import com.mobileni.taskmanager.screens.task.TaskEditScreenMain
import com.mobileni.taskmanager.ui.theme.TaskManagerTheme
import com.mobileni.taskmanager.ui.theme.TaskPalette
import com.mobileni.taskmanager.ui.theme.fonts
import com.mobileni.taskmanager.utils.toDate
import com.mobileni.taskmanager.utils.toFormattedString


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel = hiltViewModel(),
) {

    var showBottomSheet by remember { mutableStateOf(false) }
    var showActionsBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val state = viewModel.uiState

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(TaskPalette.gray),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontFamily = fonts,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = TaskPalette.blackText,
                    )
                },
                actions = {
                    IconButton(onClick = { showBottomSheet = true }) {
                        Icon(
                            imageVector = Icons.Filled.AddCircle,
                            contentDescription = "add action",
                            tint = TaskPalette.blue
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        HomeScreen(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
            tasks = state.tasks,
            onTapItem = {
                viewModel.onEvent(HomeEvent.SelectTask(it))
                showActionsBottomSheet = true
            },
            onSearch = { filter, status ->
                viewModel.onEvent(
                    HomeEvent.SearchChange(
                        filter,
                        status = TASK_STATUS_FILTERS[status]
                    )
                )
            },
            onAddTask = {
                showBottomSheet = true
            })

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                TaskEditScreenMain(task = state.selectedTask) {
                    showBottomSheet = false
                }
            }
        }

        if (showActionsBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showActionsBottomSheet = false
                },
                sheetState = sheetState
            ) {
                ActionsTaskScreen(
                    isCompleted = state.selectedTask?.completed ?: false,
                    onTapAction = {
                        when (it) {
                            TASK_ACTIONS[0] -> {
                                viewModel.onEvent(HomeEvent.CompleteTask)
                            }

                            TASK_ACTIONS[1] -> {
                                showBottomSheet = true
                            }

                            TASK_ACTIONS[2] -> {
                                viewModel.onEvent(HomeEvent.DeleteTask)
                            }
                        }
                        showActionsBottomSheet = false
                    }) {
                    showActionsBottomSheet = false
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier,
    tasks: List<Task>,
    onTapItem: (Task) -> Unit,
    onSearch: (String, Int) -> Unit,
    onAddTask: () -> Unit,
) {

    val configuration = LocalConfiguration.current

    var searchText by remember { mutableStateOf("") }

    AnimatedContent(
        targetState = tasks.isEmpty(),
        transitionSpec = {
            fadeIn(animationSpec = tween(durationMillis = 500)) togetherWith
                    fadeOut(animationSpec = tween(durationMillis = 500))
        },
        label = "animated content"
    ) { empty ->
        if (empty && searchText.isEmpty()) {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                EmptyTask {
                    onAddTask()
                }
            }
        }

        if (empty && searchText.isNotEmpty()) {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(id = R.string.no_results),
                    fontFamily = fonts,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = TaskPalette.blackText,
                )
            }
        }
    }

    Column(
        modifier = modifier
    ) {
        var selectedFilter by remember { mutableIntStateOf(0) }

        FilterTexField(
            placeholder = stringResource(id = R.string.search_by_name),
            maxLength = 10,
            onChange = {
                searchText = it
                onSearch(it, selectedFilter)
            }
        )

        LazyRow(
            Modifier.padding(start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TASK_STATUS_FILTERS.forEachIndexed { index, filter ->
                item {
                    FilterPriority(filter = filter, isSelected = selectedFilter == index) {
                        selectedFilter = index
                        onSearch(searchText, selectedFilter)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.size(16.dp))
        val expanded = configuration.screenWidthDp > 840

        if (expanded) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 300.dp),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            ) {
                items(
                    key = { tasks[it].id },
                    count = tasks.size
                ) { index ->
                    TaskItem(
                        title = tasks[index].title,
                        detail = tasks[index].detail,
                        dueDate = tasks[index].dueDate.toDate().toFormattedString(),
                        priority = tasks[index].priority,
                        isCompleted = tasks[index].completed
                    ) {
                        onTapItem(tasks[index])
                    }

                    Spacer(modifier = Modifier.size(8.dp))
                }
            }
        } else {
            LazyColumn(
                Modifier.padding(start = 16.dp, end = 16.dp),
            ) {
                items(
                    key = { tasks[it].id },
                    count = tasks.size
                ) { index ->
                    TaskItem(
                        title = tasks[index].title,
                        detail = tasks[index].detail,
                        dueDate = tasks[index].dueDate.toDate().toFormattedString(),
                        priority = tasks[index].priority,
                        isCompleted = tasks[index].completed
                    ) {
                        onTapItem(tasks[index])
                    }

                    Spacer(modifier = Modifier.size(8.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Preview(device = Devices.TABLET)
@Composable
fun HomeScreenPreview() {
    TaskManagerTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(TaskPalette.gray),
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                    ),
                    title = {
                        Text(
                            text = "Task Manager",
                            fontFamily = fonts,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            color = TaskPalette.blackText,
                        )
                    },
                    actions = {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Filled.AddCircle,
                                contentDescription = "",
                                tint = TaskPalette.blue
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            HomeScreen(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
                tasks = listOf(
                    Task(
                        id = "1",
                        title = "Song app UI design",
                        detail = "Create a home screen",
                        dueDate = 0L,
                        priority = 1,
                        completed = true
                    ),
                    Task(
                        id = "2",
                        title = "Song app UI design",
                        detail = "Create a home screen",
                        dueDate = 0L,
                        priority = 1,
                        completed = true
                    ),
                    Task(
                        id = "3",
                        title = "Song app UI design",
                        detail = "Create a home screen",
                        dueDate = 0L,
                        priority = 1,
                        completed = true
                    )
                ),
                onTapItem = { },
                onSearch = { _, _ -> }, onAddTask = {})
        }
    }
}