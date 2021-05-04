package com.hamiddev.todolist.data.repository.date

import com.hamiddev.todolist.data.repository.model.DateModel
import io.reactivex.Single
import saman.zamani.persiandate.PersianDate

interface DateRepository {
    fun getDaysOfMonth(date: PersianDate):Single<List<DateModel>>
}