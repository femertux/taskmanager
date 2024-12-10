package com.mobileni.taskmanager.screens.task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileni.domain.model.Task
import com.mobileni.domain.usecases.AddTaskUseCase
import com.mobileni.domain.usecases.UpdateTaskUseCase
import com.mobileni.taskmanager.utils.isValidDate
import com.mobileni.taskmanager.utils.toDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class TaskEditViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
) : ViewModel() {

    var uiState by mutableStateOf(TaskEditState())
        private set

    fun onEvent(event: TaskEditEvent) {
        when (event) {
            TaskEditEvent.CreateTask -> {
                createTask()
            }

            is TaskEditEvent.EditTask -> {
                updateTask(event.taskId)
            }

            is TaskEditEvent.DetailChange -> {
                uiState = uiState.copy(
                    detail = event.detail
                )
            }

            is TaskEditEvent.DueDateChange -> {
                uiState = uiState.copy(
                    dueDate = event.date
                )
            }

            is TaskEditEvent.PriorityChange -> {
                uiState = uiState.copy(
                    priority = event.priority
                )
            }

            is TaskEditEvent.TitleChange -> {
                uiState = uiState.copy(
                    name = event.name
                )
            }

            is TaskEditEvent.ValidateEditTask -> {
                uiState = uiState.copy(
                    name = event.task.title,
                    detail = event.task.detail,
                    dueDate = event.task.dueDate,
                    priority = event.task.priority
                )
            }
        }

        validateForm()
    }

    private fun createTask() {
        viewModelScope.launch {
            addTaskUseCase.invoke(
                Task(
                    id = UUID.randomUUID().toString(),
                    title = uiState.name,
                    detail = uiState.detail,
                    dueDate = uiState.dueDate,
                    priority = uiState.priority,
                    completed = false
                )
            )
        }
    }

    private fun updateTask(taskId: String) {
        viewModelScope.launch {
            updateTaskUseCase.invoke(
                Task(
                    id = taskId,
                    title = uiState.name,
                    detail = uiState.detail,
                    dueDate = uiState.dueDate,
                    priority = uiState.priority,
                    completed = false
                )
            )
        }
    }

    private fun validateForm() {
        if (uiState.name.isEmpty() && uiState.detail.isEmpty()) {
            uiState = uiState.copy(isCompleted = false)
            return
        }

        if (!uiState.dueDate.toDate().isValidDate()) {
            uiState = uiState.copy(isCompleted = false)
            return
        }

        uiState = uiState.copy(isCompleted = true)
    }
}