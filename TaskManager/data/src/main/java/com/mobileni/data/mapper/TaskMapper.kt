package com.mobileni.data.mapper

import com.mobileni.data.database.entity.TaskEntity
import com.mobileni.domain.model.Task

fun Task.toEntity() = TaskEntity(
    id, title, detail, dueDate, priority, completed
)

fun TaskEntity.toDomain() = Task(
    id, title, detail, dueDate, priority, completed
)