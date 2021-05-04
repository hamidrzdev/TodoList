package com.hamiddev.todolist.feature.category

import androidx.lifecycle.MutableLiveData
import com.hamiddev.todolist.common.BaseCompletableObserver
import com.hamiddev.todolist.common.BaseSingleObserver
import com.hamiddev.todolist.common.BaseViewModel
import com.hamiddev.todolist.data.repository.model.CategoryModel
import com.hamiddev.todolist.data.repository.category.CategoryRepository
import io.reactivex.schedulers.Schedulers

class CategoryViewModel(private val categoryRepository: CategoryRepository) : BaseViewModel() {
    val categoriesLiveData=MutableLiveData<List<CategoryModel>>()
    val messageLiveData=MutableLiveData<String>()
    val newCategoryLiveData=MutableLiveData<CategoryModel>()

    init {
        categories()
    }


    fun categories(){
        progressBarLiveData.value = true
        categoryRepository.categories()
            .subscribeOn(Schedulers.io())
            .doFinally { progressBarLiveData.postValue(false) }
            .subscribe(object :BaseSingleObserver<List<CategoryModel>>(compositeDisposable){
                override fun onSuccess(t: List<CategoryModel>) {
                    categoriesLiveData.postValue(t)
                }
            })
    }

    fun add(categoryModel: CategoryModel){
        categoryRepository.add(categoryModel)
            .subscribeOn(Schedulers.io())
            .subscribe(object :BaseCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    messageLiveData.postValue("دسته بندی اضافه شد")
                    newCategoryLiveData.postValue(categoryModel)
                }
            })
    }


    fun remove(categoryModel: CategoryModel){
        categoryRepository.remove(categoryModel)
            .subscribeOn(Schedulers.io())
            .subscribe(object :BaseCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    messageLiveData.postValue("دسته بندی حذف شد")
                    newCategoryLiveData.postValue(categoryModel)
                }
            })
    }
}