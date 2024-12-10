package com.mobileni.taskmanager.screens.task

data class TaskEditState(
    val name: String = "",
    val detail: String = "",
    val dueDate: Long = 0L,
    val priority: Int = 0,
    val isCompleted: Boolean = false
)