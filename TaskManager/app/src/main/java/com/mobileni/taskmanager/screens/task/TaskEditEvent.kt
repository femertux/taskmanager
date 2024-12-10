package com.mobileni.taskmanager.screens.task

import com.mobileni.domain.model.Task

sealed interface TaskEditEvent {
    data class TitleChange(val name: String) : TaskEditEvent
    data class DetailChange(val detail: String) : TaskEditEvent
    data class PriorityChange(val priority: Int) : TaskEditEvent
    data class DueDateChange(val date: Long) : TaskEditEvent
    object CreateTask : TaskEditEvent
    data class EditTask(val taskId: String) : TaskEditEvent
    data class  ValidateEditTask(val task: Task) : TaskEditEvent
}