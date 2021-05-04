package com.hamiddev.todolist.data.repository.date

import com.hamiddev.todolist.data.repository.model.DateModel
import io.reactivex.Single
import saman.zamani.persiandate.PersianDate

class DateRepositoryImpl : DateRepository {
    override fun getDaysOfMonth(date: PersianDate): Single<List<DateModel>> =
        Single.create {
            if (!it.isDisposed) {
                try {
                    it.onSuccess(getDays(date))
                } catch (e: Exception) {
                    it.onError(e)
                }
            }
        }

    fun getDays(date: PersianDate): MutableList<DateModel> {
        val dateList = mutableListOf<DateModel>()
        for (i in 1..date.monthDays) {
            dateList.add(DateModel(day(date, i), i, date.monthName(), date.shYear,date.shMonth))
        }
        return if (dateList.isNotEmpty()) dateList else throw IllegalStateException("The list is empty")
    }

    fun day(persianDate: PersianDate, day: Int): String {
        val date = PersianDate().apply {
            shDay = day
            shMonth = persianDate.shMonth
            shYear = persianDate.shYear
        }
        return date.dayName()
    }
}