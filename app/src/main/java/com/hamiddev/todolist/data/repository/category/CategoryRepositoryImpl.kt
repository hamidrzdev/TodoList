package com.hamiddev.todolist.data.repository.category

import com.hamiddev.todolist.data.repository.model.CategoryModel
import com.hamiddev.todolist.databse.CategoryDao
import io.reactivex.Completable
import io.reactivex.Single

class CategoryRepositoryImpl(private val categoryDao: CategoryDao) : CategoryRepository {
    override fun categories(): Single<List<CategoryModel>> =
        categoryDao.categories()

    override fun add(categoryModel: CategoryModel): Completable =
        categoryDao.add(categoryModel)

    override fun remove(categoryModel: CategoryModel): Completable =
        categoryDao.remove(categoryModel)
}