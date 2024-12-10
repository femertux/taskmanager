package com.mobileni.domain.usecases

import com.mobileni.domain.repository.TaskRepository

class DeleteTaskUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(taskId: String) {
        return repository.deleteTask(taskId)
    }
}