package com.mobileni.domain.usecases

import com.mobileni.domain.model.Task
import com.mobileni.domain.repository.TaskRepository

class AddTaskUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(task: Task) {
        return repository.addTask(task)
    }
}