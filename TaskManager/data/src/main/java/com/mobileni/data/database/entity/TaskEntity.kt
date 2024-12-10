package com.mobileni.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey val id: String,
    val title: String,
    val detail: String,
    val dueDate: Long,
    val priority: Int,
    val completed: Boolean,
)