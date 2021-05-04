package com.hamiddev.todolist.feature.home

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hamiddev.todolist.R
import com.hamiddev.todolist.data.repository.model.DateModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_date.view.*
import saman.zamani.persiandate.PersianDate
import timber.log.Timber
import java.util.*

var selectedPosition = PersianDate().shDay-1


class DateListAdapter(private val context: Context) :
    RecyclerView.Adapter<DateListAdapter.DateListViewHolder>() {
    var onDayClickListener: OnDayClickListener? = null
    var dateList = mutableListOf<DateModel>()
    set(value) {
        field=value
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateListViewHolder =
        DateListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_date, parent, false)
        )

    override fun onBindViewHolder(holder: DateListViewHolder, position: Int) {
        holder.bindDate(dateList[position])
        if (selectedPosition == position) {
            holder.containerView.itemDayNumber.setBackgroundResource(R.drawable.circle_background)
            holder.containerView.itemDayNumber.setTextColor(ContextCompat.getColor(context,R.color.blue))
        } else {
            holder.containerView.itemDayNumber.setBackgroundColor(Color.TRANSPARENT)
            holder.containerView.itemDayNumber.setTextColor(ContextCompat.getColor(context,R.color.white))
        }

    }

    override fun getItemCount(): Int = dateList.size


    inner class DateListViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),LayoutContainer {
        fun bindDate(dateModel: DateModel) {
            containerView.itemDayName.text = dateModel.dayName
            containerView.itemDayNumber.text = dateModel.dayNumber.toString()

            containerView.setOnClickListener {
                    if (selectedPosition == RecyclerView.NO_POSITION) return@setOnClickListener

                notifyItemChanged(selectedPosition)
                selectedPosition = adapterPosition
                notifyItemChanged(selectedPosition)
                onDayClickListener!!.onDayClicked(dateList[adapterPosition],adapterPosition)
            }
        }
    }



    interface OnDayClickListener {
        fun onDayClicked(dateModel: DateModel, position: Int)
    }
}


