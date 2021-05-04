package com.hamiddev.todolist.databse

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hamiddev.todolist.data.repository.model.CategoryModel
import com.hamiddev.todolist.data.repository.model.TaskModel
import com.hamiddev.todolist.databse.CategoryDao
import com.hamiddev.todolist.databse.TaskDao

@Database(entities = [TaskModel::class, CategoryModel::class], exportSchema = false, version = 1)
abstract class DatabaseManager : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun categoryDao(): CategoryDao
}