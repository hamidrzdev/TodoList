package com.hamiddev.todolist.data.repository.task

import com.hamiddev.todolist.data.repository.model.DateModel
import com.hamiddev.todolist.data.repository.model.TaskModel
import com.hamiddev.todolist.databse.TaskDao
import io.reactivex.Completable
import io.reactivex.Single
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat

class TaskRepositoryImpl(private val taskDao: TaskDao) : TaskRepository {
    override fun tasks(date: DateModel): Single<List<TaskModel>> =
        taskDao.tasks(PersianDateFormat.format(PersianDate().apply {
            shYear=date.year
            shMonth=date.monthNumber
            shDay=date.dayNumber
        }, "Y-m-d"))

    override fun addTask(taskModel: TaskModel): Completable =
        taskDao.addTask(taskModel)

    override fun updateTask(taskModel: TaskModel): Completable =
        taskDao.updateTask(taskModel)

    override fun removeTask(taskModel: TaskModel): Completable =
        taskDao.removeTask(taskModel)
}