package com.hamiddev.todolist.data.repository.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class TaskModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int?=null,
    var category:String,
    var title:String,
    var date:String,
    @ColumnInfo(name = "start_hour")
    var startHour:Int,
    @ColumnInfo(name = "end_hour")
    var endHour:Int,
    var isComplete:Boolean = false
)