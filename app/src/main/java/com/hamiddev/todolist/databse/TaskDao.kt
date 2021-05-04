package com.hamiddev.todolist.databse

import androidx.room.*
import com.hamiddev.todolist.data.repository.model.TaskModel
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface TaskDao {

    @Query("SELECT * FROM task WHERE date = :date")
    fun tasks(date: String): Single<List<TaskModel>>

    @Insert
    fun addTask(taskModel: TaskModel): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTask(taskModel: TaskModel): Completable

    @Delete
    fun removeTask(taskModel: TaskModel): Completable
}