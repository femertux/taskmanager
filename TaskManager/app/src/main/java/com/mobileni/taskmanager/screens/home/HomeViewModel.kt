package com.mobileni.taskmanager.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileni.domain.model.Task
import com.mobileni.domain.usecases.DeleteTaskUseCase
import com.mobileni.domain.usecases.GetTasksUseCase
import com.mobileni.domain.usecases.UpdateTaskUseCase
import com.mobileni.domain.utils.Constants.TASK_STATUS_FILTERS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {
    var uiState by mutableStateOf(HomeState())
        private set

    init {
        getAllTasks(filter = "")
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.CompleteTask -> {
                uiState.selectedTask?.let {
                    updateTask(it.copy(completed = true))
                }
            }

            HomeEvent.EditTask -> {
                uiState.selectedTask?.let {
                    updateTask(it)
                }
            }

            HomeEvent.DeleteTask -> {
                uiState.selectedTask?.let {
                    deleteTask(it.id)
                }
            }

            is HomeEvent.SelectTask -> {
                uiState = uiState.copy(selectedTask = event.task)
            }

            is HomeEvent.PriorityChange -> {}
            is HomeEvent.SearchChange -> {
                getAllTasks(filter = event.name, status = event.status)
            }
        }
    }

    private fun updateTask(task: Task) {
        viewModelScope.launch {
            updateTaskUseCase.invoke(task)
            uiState = uiState.copy(selectedTask = null)
        }
    }

    private fun deleteTask(taskId: String) {
        viewModelScope.launch {
            deleteTaskUseCase.invoke(taskId)
            uiState = uiState.copy(selectedTask = null)
        }
    }

    private fun getAllTasks(filter: String, status: String = TASK_STATUS_FILTERS[0]) {
        viewModelScope.launch {
            getTasksUseCase.invoke(filter, status).collect { result ->
                uiState = uiState.copy(tasks = result, selectedTask = null)
            }
        }
    }

}