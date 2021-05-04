package com.hamiddev

import android.app.Application
import androidx.room.Room
import com.hamiddev.todolist.databse.DatabaseManager
import com.hamiddev.todolist.data.repository.*
import com.hamiddev.todolist.data.repository.category.CategoryRepository
import com.hamiddev.todolist.data.repository.category.CategoryRepositoryImpl
import com.hamiddev.todolist.data.repository.date.DateRepository
import com.hamiddev.todolist.data.repository.date.DateRepositoryImpl
import com.hamiddev.todolist.data.repository.task.TaskRepository
import com.hamiddev.todolist.data.repository.task.TaskRepositoryImpl
import com.hamiddev.todolist.feature.category.CategoryViewModel
import com.hamiddev.todolist.feature.home.HomeViewModel
import com.hamiddev.todolist.feature.task.TaskViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        val myModule = module {
            single { Room.databaseBuilder(this@App, DatabaseManager::class.java, "app:db").build() }

            single<DateRepository> { DateRepositoryImpl() }
            single<TaskRepository> { TaskRepositoryImpl(get<DatabaseManager>().taskDao()) }
            single<CategoryRepository> { CategoryRepositoryImpl(get<DatabaseManager>().categoryDao()) }

            viewModel { HomeViewModel(get(), get()) }
            viewModel { CategoryViewModel(get()) }
            viewModel { TaskViewModel(get(),get()) }
        }


        startKoin {
            androidContext(this@App)
            modules(myModule)
        }
    }
}