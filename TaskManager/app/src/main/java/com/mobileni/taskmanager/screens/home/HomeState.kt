package com.mobileni.taskmanager.screens.home

import com.mobileni.domain.model.Task

data class HomeState(
    val tasks: List<Task> = emptyList(),
    val selectedTask: Task? = null
)