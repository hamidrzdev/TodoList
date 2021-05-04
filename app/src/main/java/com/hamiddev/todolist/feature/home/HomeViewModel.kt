package com.hamiddev.todolist.feature.home

import androidx.lifecycle.MutableLiveData
import com.hamiddev.todolist.common.BaseCompletableObserver
import com.hamiddev.todolist.common.BaseSingleObserver
import com.hamiddev.todolist.common.BaseViewModel
import com.hamiddev.todolist.data.repository.model.DateModel
import com.hamiddev.todolist.data.repository.model.TaskModel
import com.hamiddev.todolist.data.repository.date.DateRepository
import com.hamiddev.todolist.data.repository.task.TaskRepository
import io.reactivex.schedulers.Schedulers
import saman.zamani.persiandate.PersianDate
import timber.log.Timber

class HomeViewModel(
    private val dateRepository: DateRepository,
    private val taskRepository: TaskRepository
) : BaseViewModel() {
    val daysLiveData = MutableLiveData<List<DateModel>>()
    val tasksLiveData = MutableLiveData<List<TaskModel>>()
    val updatedTaskLiveData = MutableLiveData<TaskModel>()
    val removedTaskLiveData = MutableLiveData<TaskModel>()

    init {
        getDays(PersianDate().apply {
            shYear = PersianDate().shYear
            shMonth = PersianDate().shMonth
        })
    }


    fun getDays(date: PersianDate) {
        progressBarLiveData.value=true
        dateRepository.getDaysOfMonth(date)
            .subscribeOn(Schedulers.io())
            .doFinally { progressBarLiveData.postValue(false) }
            .subscribe(object : BaseSingleObserver<List<DateModel>>(compositeDisposable) {
                override fun onSuccess(t: List<DateModel>) {
                    daysLiveData.postValue(t)
                }
            })
    }


    fun tasks(date: DateModel) {
        progressBarLiveData.value=true
        taskRepository.tasks(date)
            .subscribeOn(Schedulers.io())
            .doFinally { progressBarLiveData.postValue(false) }
            .subscribe(object : BaseSingleObserver<List<TaskModel>>(compositeDisposable) {
                override fun onSuccess(t: List<TaskModel>) {
                    tasksLiveData.postValue(t)
                }
            })
    }

    fun updateTask(taskModel: TaskModel){
        taskRepository.updateTask(taskModel)
            .subscribeOn(Schedulers.io())
            .subscribe(object :BaseCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    Timber.i("llll   complete")
                }
            })
    }
    fun removeTask(taskModel: TaskModel){
        taskRepository.removeTask(taskModel)
            .subscribeOn(Schedulers.io())
            .subscribe(object :BaseCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    Timber.i("llll   complete")
                    removedTaskLiveData.postValue(taskModel)
                }
            })
    }


}