package com.hamiddev.todolist.feature.task

import androidx.lifecycle.MutableLiveData
import com.hamiddev.todolist.common.BaseCompletableObserver
import com.hamiddev.todolist.common.BaseSingleObserver
import com.hamiddev.todolist.common.BaseViewModel
import com.hamiddev.todolist.data.repository.model.CategoryModel
import com.hamiddev.todolist.data.repository.model.TaskModel
import com.hamiddev.todolist.data.repository.category.CategoryRepository
import com.hamiddev.todolist.data.repository.task.TaskRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class TaskViewModel(
    private val categoryRepository: CategoryRepository,
    private val taskRepository: TaskRepository
) : BaseViewModel() {
    val categoriesLiveData = MutableLiveData<List<CategoryModel>>()

    init {
        categories()
    }

    fun categories() {
        progressBarLiveData.value = true
        categoryRepository.categories()
            .subscribeOn(Schedulers.io())
            .doFinally { progressBarLiveData.postValue(false) }
            .subscribe(object : BaseSingleObserver<List<CategoryModel>>(compositeDisposable) {
                override fun onSuccess(t: List<CategoryModel>) {
                    categoriesLiveData.postValue(t)
                }

            })
    }

    fun addTask(taskModel: TaskModel) {
        taskRepository.addTask(taskModel)
            .subscribeOn(Schedulers.io())
            .subscribe(object : BaseCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    Timber.i("task added successfully")
                }
            })
    }


    fun addCategory(categoryModel: CategoryModel) {
        categoryRepository.add(categoryModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    categories()
                }
            })
    }

}