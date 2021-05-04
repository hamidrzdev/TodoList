package com.hamiddev.todolist.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hamiddev.todolist.R
import com.hamiddev.todolist.common.BaseFragment
import com.hamiddev.todolist.data.repository.model.DateModel
import com.hamiddev.todolist.data.repository.model.TaskModel
import kotlinx.android.synthetic.main.home_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat

class FragmentHome:BaseFragment(), DateListAdapter.OnDayClickListener, TaskAdapter.TaskCallBack {
    val viewModel:HomeViewModel by viewModel()
    var daysAdapter:DateListAdapter? = null
    var tasksAdapter:TaskAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment,container,false)
    }

    override fun onStart() {
        super.onStart()
        viewModel.tasks(
            DateModel(
                PersianDateFormat.format(PersianDate(), "j"), PersianDate().shDay,
                PersianDateFormat.format(
                    PersianDate(), "F"
                ),
                PersianDate().shYear,
                PersianDate().shMonth
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.progressBarLiveData.observe(viewLifecycleOwner){
            setProgressIndicator(it)
        }



        viewModel.daysLiveData.observe(viewLifecycleOwner){
            daysAdapter = DateListAdapter(requireContext())
            daysList.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,true)
            daysAdapter!!.dateList = it.toMutableList()
            daysList.adapter=daysAdapter
            daysList.post {
                daysList.smoothScrollToPosition(PersianDate().shDay-1)

            }

            daysAdapter!!.onDayClickListener = this
        }

        viewModel.tasksLiveData.observe(viewLifecycleOwner){
            emptyState.visibility = if (it.isEmpty()) View.VISIBLE else View.INVISIBLE
            tasksAdapter = TaskAdapter(requireContext())
            taskList.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
            tasksAdapter!!.taskList = it.toMutableList()
            taskList.adapter=tasksAdapter
            tasksAdapter!!.taskCallBack=this
        }

        viewModel.removedTaskLiveData.observe(viewLifecycleOwner){
            tasksAdapter!!.remove(it)
        }

    }

    override fun onDayClicked(dateModel: DateModel, position: Int) {
        viewModel.tasks(dateModel)
    }

    override fun updateTask(task: TaskModel) {
        viewModel.updateTask(task)
    }

    override fun removeTask(task: TaskModel) {
        viewModel.removeTask(task)
    }


}