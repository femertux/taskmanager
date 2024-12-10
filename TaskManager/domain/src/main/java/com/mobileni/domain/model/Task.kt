package com.mobileni.domain.model

data class Task(
    val id: String,
    val title: String,
    val detail: String,
    val dueDate: Long,
    val priority: Int,
    val completed: Boolean,
)