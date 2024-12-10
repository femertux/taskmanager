package com.mobileni.taskmanager.screens.home

import com.mobileni.domain.model.Task

sealed interface HomeEvent {
    data class SearchChange(val name: String, val status: String) : HomeEvent
    data class PriorityChange(val priority: Int) : HomeEvent
    data class SelectTask(val task: Task) : HomeEvent
    object CompleteTask : HomeEvent
    object EditTask : HomeEvent
    object DeleteTask : HomeEvent
}