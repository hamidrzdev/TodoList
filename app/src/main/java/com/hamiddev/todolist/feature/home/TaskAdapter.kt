package com.hamiddev.todolist.feature.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hamiddev.todolist.R
import com.hamiddev.todolist.data.repository.model.TaskModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.task_item.view.*

class TaskAdapter(val context: Context) : RecyclerView.Adapter<TaskAdapter.TaskAdapterHolder>() {
    var taskCallBack: TaskCallBack? = null

    var taskList = mutableListOf<TaskModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class TaskAdapterHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(task: TaskModel) {
            containerView.categoryTv.text = task.category
            containerView.titleTv.text = task.title
            containerView.timeTv.text = "${task.startHour}:00 - ${task.endHour}:00"

            containerView.isComplete.setOnClickListener {
                task.isComplete =!task.isComplete
                taskCallBack?.updateTask(task)
            }

            containerView.deleteTaskBtn.setOnClickListener {
                taskCallBack?.removeTask(task)
            }

            containerView.isComplete.isChecked = task.isComplete
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapterHolder =
        TaskAdapterHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        )

    override fun onBindViewHolder(holder: TaskAdapterHolder, position: Int) {
        holder.bind(taskList[position])
    }

    override fun getItemCount(): Int = taskList.size

    interface TaskCallBack {
        fun updateTask(task: TaskModel)
        fun removeTask(task: TaskModel)
    }

    fun remove(task: TaskModel) {
        val index = taskList.indexOf(task)
        taskList.removeAt(index)
        notifyItemRemoved(index)
    }

}