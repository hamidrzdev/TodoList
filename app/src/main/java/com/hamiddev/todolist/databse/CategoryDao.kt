package com.hamiddev.todolist.databse

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hamiddev.todolist.data.repository.model.CategoryModel
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category")
    fun categories():Single<List<CategoryModel>>

    @Insert
    fun add(categoryModel: CategoryModel):Completable

    @Delete
    fun remove(categoryModel: CategoryModel):Completable
}