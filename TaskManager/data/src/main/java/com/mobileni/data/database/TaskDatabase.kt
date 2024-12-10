package com.mobileni.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mobileni.data.database.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

}