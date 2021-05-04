package com.hamiddev.todolist.feature.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hamiddev.todolist.R
import com.hamiddev.todolist.data.repository.model.ColorModel
import kotlinx.android.synthetic.main.add_category_dialog.*

class AddCategoryDialog : BottomSheetDialogFragment(), ColorListAdapter.ColorItemEventListener {
    var categoryCallBack: CategoryCallBack? = null
    var content: String? = null
    val adapter = ColorListAdapter()
    @DrawableRes
    var colorBackground: Int = R.drawable.blue_circle
    val colorResources =
        mutableListOf(ColorModel(R.drawable.blue_circle), ColorModel(R.drawable.black_circle))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_category_dialog, null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        colorRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        adapter.colorList = colorResources
        adapter.colorItemEventListener = this
        colorRv.adapter = adapter

        addBtn.setOnClickListener {
            categoryCallBack?.onButtonClicked(addEt.text.toString(),colorBackground)
            dismiss()
        }

    }

    interface CategoryCallBack {
        fun onButtonClicked(title: String, color: Int)
    }

    override fun onItemClicked(colorModel: ColorModel) {
        colorBackground = colorModel.color
    }
}