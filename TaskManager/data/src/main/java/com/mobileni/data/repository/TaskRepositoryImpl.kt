package com.mobileni.data.repository

import com.mobileni.data.DispatcherProvider
import com.mobileni.data.database.TaskDao
import com.mobileni.data.mapper.toDomain
import com.mobileni.data.mapper.toEntity
import com.mobileni.domain.model.Task
import com.mobileni.domain.repository.TaskRepository
import com.mobileni.domain.utils.Constants.TASK_STATUS_FILTERS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TaskRepositoryImpl(
    private val taskDao: TaskDao,
    private val dispatcherProvider: DispatcherProvider,
) : TaskRepository {
    override fun getAllTasks(filter: String, status: String): Flow<List<Task>> {
        return flow {
            taskDao.getAllTask(filter).collect { tasks ->
                val list = tasks.map { it.toDomain() }
                    .filter { status == TASK_STATUS_FILTERS[0] || (status == TASK_STATUS_FILTERS[1] && !it.completed) || (status == TASK_STATUS_FILTERS[2] && it.completed) }
                emit(list)
            }
        }.flowOn(dispatcherProvider.io())
    }

    override suspend fun addTask(task: Task) {
        val entity = task.toEntity()
        taskDao.insertTask(entity)
    }

    override suspend fun updateTask(task: Task) {
        taskDao.insertTask(task.toEntity())
    }

    override suspend fun deleteTask(taskId: String) {
        taskDao.deleteTask(taskId)
    }

}