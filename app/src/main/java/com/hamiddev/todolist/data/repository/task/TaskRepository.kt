package com.hamiddev.todolist.data.repository.task

import com.hamiddev.todolist.data.repository.model.DateModel
import com.hamiddev.todolist.data.repository.model.TaskModel
import io.reactivex.Completable
import io.reactivex.Single

interface TaskRepository {
    fun tasks(date: DateModel):Single<List<TaskModel>>
    fun addTask(taskModel: TaskModel):Completable
    fun updateTask(taskModel: TaskModel):Completable
    fun removeTask(taskModel: TaskModel):Completable
}