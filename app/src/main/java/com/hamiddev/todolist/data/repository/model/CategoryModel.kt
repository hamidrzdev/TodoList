package com.hamiddev.todolist.data.repository.model

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryModel(
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    @DrawableRes val color:Int,
    val title:String
)