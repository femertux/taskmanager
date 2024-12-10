package com.mobileni.data.di

import com.mobileni.domain.repository.TaskRepository
import com.mobileni.domain.usecases.AddTaskUseCase
import com.mobileni.domain.usecases.DeleteTaskUseCase
import com.mobileni.domain.usecases.GetTasksUseCase
import com.mobileni.domain.usecases.UpdateTaskUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetTasksUseCase(
        repository: TaskRepository,
    ): GetTasksUseCase {
        return GetTasksUseCase(
            repository
        )
    }

    @Provides
    @ViewModelScoped
    fun provideAddTaskUseCase(
        repository: TaskRepository,
    ): AddTaskUseCase {
        return AddTaskUseCase(
            repository
        )
    }

    @Provides
    @ViewModelScoped
    fun provideDeleteTaskUseCase(
        repository: TaskRepository,
    ): DeleteTaskUseCase {
        return DeleteTaskUseCase(
            repository
        )
    }

    @Provides
    @ViewModelScoped
    fun provideUpdateTaskUseCase(
        repository: TaskRepository,
    ): UpdateTaskUseCase {
        return UpdateTaskUseCase(
            repository
        )
    }

}