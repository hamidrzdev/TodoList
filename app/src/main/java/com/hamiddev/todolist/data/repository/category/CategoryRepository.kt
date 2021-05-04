package com.hamiddev.todolist.data.repository.category

import com.hamiddev.todolist.data.repository.model.CategoryModel
import io.reactivex.Completable
import io.reactivex.Single

interface CategoryRepository {
    fun categories(): Single<List<CategoryModel>>
    fun add(categoryModel: CategoryModel): Completable
    fun remove(categoryModel: CategoryModel):Completable
}