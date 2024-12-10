package com.mobileni.data.di

import com.mobileni.data.DispatcherProvider
import com.mobileni.data.database.TaskDao
import com.mobileni.data.repository.TaskRepositoryImpl
import com.mobileni.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideTaskRepository(
        taskDao: TaskDao,
        dispatcherProvider: DispatcherProvider,
    ): TaskRepository {
        return TaskRepositoryImpl(taskDao, dispatcherProvider)
    }

}