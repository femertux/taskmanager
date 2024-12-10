package com.mobileni.domain.usecases

import com.mobileni.domain.model.Task
import com.mobileni.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetTasksUseCase(private val repository: TaskRepository) {
    operator fun invoke(filter: String, status: String): Flow<List<Task>> {
        return repository.getAllTasks(filter, status)
    }
}