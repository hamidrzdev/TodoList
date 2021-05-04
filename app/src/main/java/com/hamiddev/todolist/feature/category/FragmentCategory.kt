package com.hamiddev.todolist.feature.category

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.google.android.material.chip.Chip
import com.hamiddev.todolist.R
import com.hamiddev.todolist.common.BaseFragment
import com.hamiddev.todolist.common.convertDpToPixel
import com.hamiddev.todolist.data.repository.model.CategoryModel
import kotlinx.android.synthetic.main.category_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class FragmentCategory : BaseFragment(), AddCategoryDialog.CategoryCallBack {
    private val viewModel: CategoryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.category_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        viewModel.categoriesLiveData.observe(viewLifecycleOwner) {
            it.forEach { categoryItem ->
                categoryGp.addView(Chip(requireContext()).apply {
                    initChip(this,categoryItem)
                }, 0)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel.progressBarLiveData.observe(viewLifecycleOwner){
            setProgressIndicator(it)
        }

        categoryGp.children.forEach {
            (it as Chip)
            it.setOnClickListener {
                Toast.makeText(requireContext(), "clicked", Toast.LENGTH_SHORT).show()
            }
        }

        addCategory.setOnClickListener {
            val categoryDialog = AddCategoryDialog()
            categoryDialog.show(childFragmentManager, null)
            categoryDialog.categoryCallBack = this@FragmentCategory
        }



        viewModel.newCategoryLiveData.observe(viewLifecycleOwner) {
            categoryGp.addView(Chip(requireContext()).apply {
                initChip(this,it)
            }, 0)
        }

    }

    override fun onButtonClicked(title: String, color: Int) {
        viewModel.add(CategoryModel(null, color, title))
    }

    fun getStateColor(color: Int): ColorStateList =
        ColorStateList.valueOf(ContextCompat.getColor(requireContext(), color))

    fun getColor(color: Int): Int =
        ContextCompat.getColor(requireContext(), color)

    fun initChip(chip: Chip,categoryModel: CategoryModel){
        chip.gravity = Gravity.CENTER
        when (categoryModel.color) {
            R.drawable.blue_circle -> {
                chip.chipStrokeColor = getStateColor(R.color.blue)
                chip.setTextColor(getColor(R.color.blue))
            }
            R.drawable.black_circle -> {
                chip.chipStrokeColor = getStateColor(R.color.black)
                chip.setTextColor(getColor(R.color.black))
            }
        }
        chip.textSize = 16f
        chip.chipStrokeWidth = convertDpToPixel(2f, requireContext())
        chip.chipEndPadding = convertDpToPixel(15f, requireContext())
        chip.chipStartPadding = convertDpToPixel(15f, requireContext())
        chip.text = categoryModel.title
    }


}