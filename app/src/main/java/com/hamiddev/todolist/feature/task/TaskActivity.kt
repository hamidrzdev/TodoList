package com.hamiddev.todolist.feature.task

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.hamiddev.todolist.R
import com.hamiddev.todolist.common.BaseActivity
import com.hamiddev.todolist.common.convertDpToPixel
import com.hamiddev.todolist.data.repository.model.CategoryModel
import com.hamiddev.todolist.data.repository.model.TaskModel
import com.hamiddev.todolist.feature.category.AddCategoryDialog
import com.hamiddev.todolist.ui.datePicker.Listener
import com.hamiddev.todolist.ui.datePicker.PersianDatePickerDialog
import com.hamiddev.todolist.ui.datePicker.util.PersianCalendar
import com.hamiddev.todolist.ui.scroll.ObservableScrollViewCallbacks
import com.hamiddev.todolist.ui.scroll.ScrollState
import kotlinx.android.synthetic.main.task_activity.*
import org.koin.android.viewmodel.ext.android.viewModel
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat

class TaskActivity : BaseActivity(), AddCategoryDialog.CategoryCallBack {
    private val viewModel: TaskViewModel by viewModel()
    var title: String? = null
    var date: String? = null
    var startHour: Int? = null
    var endHour: Int? = null
    var category: String? = null
    var descr: String? = null

    var categoryModel: CategoryModel? = null

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.task_activity)

        viewModel.progressBarLiveData.observe(this) {
            setProgressIndicator(it)
        }

        val hourList = listOf(
            "00:00",
            "01:00",
            "02:00",
            "03:00",
            "04:00",
            "05:00",
            "06:00",
            "07:00",
            "08:00",
            "09:00",
            "10:00",
            "11:00",
            "12:00",
            "13:00",
            "14:00",
            "15:00",
            "16:00",
            "17:00",
            "18:00",
            "19:00",
            "20:00",
            "21:00",
            "22:00",
            "23:00",
            "24:00"
        )

        val hourAdapter = ArrayAdapter(this, R.layout.item_hour, hourList)
        startHourEt.post {
            startHourEt.setAdapter(hourAdapter)

        }
        endHourEt.post {
            endHourEt.setAdapter(hourAdapter)
        }

        addCategoryBtn.setOnClickListener {
            val categoryDialog = AddCategoryDialog()
            categoryDialog.show(supportFragmentManager, null)
            categoryDialog.categoryCallBack = this@TaskActivity
        }

        addTaskBtn.setOnClickListener { view ->

            if (
                titleEt.text!!.isNotEmpty() &&
                datePicker.text!!.isNotEmpty() &&
                startHourEt.text!!.isNotEmpty() &&
                endHourEt.text!!.isNotEmpty() &&
                descriptionEt.text!!.isNotEmpty() &&
                categoryGp.checkedChipId != -1
            ) {

                val checkedChip = categoryGp.findViewById<Chip>(categoryGp.checkedChipId)
                checkedChip?.let { chip ->
                    category = chip.text.toString()
                }
                descr = description.text.toString()
                title = titleEt.text.toString()
                startHour = startHourEt.text.substring(0, 2).toInt()
                endHour = endHourEt.text.substring(0, 2).toInt()

                viewModel.addTask(
                    TaskModel(
                        null,
                        category!!,
                        title!!,
                        date!!,
                        startHour!!,
                        endHour!!
                    )
                )
                finish()
            } else
                Snackbar.make(view, "فیلد ها را تکمیل کنید", Snackbar.LENGTH_SHORT).show()
        }

        viewModel.categoriesLiveData.observe(this) {
            categoryContainer.visibility = if (it.isEmpty()) View.VISIBLE else View.INVISIBLE

            it.forEach { categoryModel ->
                categoryGp.addView(Chip(this).apply {
                    gravity = Gravity.CENTER
                    isCheckable = true
                    textSize = 16f
                    chipStrokeWidth = convertDpToPixel(2f, this@TaskActivity)
                    chipEndPadding = convertDpToPixel(15f, this@TaskActivity)
                    chipStartPadding = convertDpToPixel(15f, this@TaskActivity)
                    setCheckedIconResource(R.drawable.ic_baseline_check_circle_24_white)
                    text = categoryModel.title
                    chipBackgroundColor =
                        ColorStateList.valueOf(Color.TRANSPARENT)
                    when (categoryModel.color) {
                        R.drawable.blue_circle -> whenColor(this, R.color.blue)
                        R.drawable.black_circle -> whenColor(this, R.color.black)
                    }
                })
            }


        }


        val initDay = PersianCalendar().apply {
            setPersianDate(
                PersianDatePickerDialog.THIS_YEAR,
                PersianCalendar.MONTH,
                PersianCalendar.DAY_OF_MONTH
            )
        }

        datePicker.setOnClickListener {
            val persianDatePickerDialog = PersianDatePickerDialog(this)
            persianDatePickerDialog.setMinYear(PersianDatePickerDialog.THIS_YEAR)
            persianDatePickerDialog.setMaxYear(1450)
            persianDatePickerDialog.setInitDate(initDay)
            persianDatePickerDialog.setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
            persianDatePickerDialog.showDay(true)
            persianDatePickerDialog.setShowInBottomSheet(true)
                .setListener(object : Listener {
                    override fun onDateSelected(persianCalendar: PersianCalendar?) {
                        val persianDate = PersianDate(persianCalendar!!.time)
                        val persianDateFormat = PersianDateFormat("j F Y")
                        datePicker.setText(persianDateFormat.format(persianDate))
                        date = PersianDateFormat.format(persianDate, "Y-m-d")
                    }

                    override fun onDismissed() {

                    }

                }).show()
        }

        val container0 = cont0
        observableScrollView.addScrollViewCallbacks(object : ObservableScrollViewCallbacks {
            override fun onScrollChanged(scrollY: Int, firstScroll: Boolean, dragging: Boolean) {

                cont0.post {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        container0.translationY = scrollY.toFloat() / 4
                    }
                }
            }

            override fun onDownMotionEvent() {
                TODO("Not yet implemented")
            }

            override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun whenColor(chip: Chip, color: Int) {
        chip.chipStrokeColor = ColorStateList.valueOf(ContextCompat.getColor(this, color))
        chip.setTextColor(ContextCompat.getColor(this, color))
        chip.checkedIconTint = ColorStateList.valueOf(ContextCompat.getColor(this, color))
    }

    override fun onButtonClicked(title: String, color: Int) {
        viewModel.addCategory(CategoryModel(null, color, title))
    }
}