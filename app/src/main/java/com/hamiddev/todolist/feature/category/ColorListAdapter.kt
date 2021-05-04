package com.hamiddev.todolist.feature.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hamiddev.todolist.R
import com.hamiddev.todolist.data.repository.model.ColorModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_color.view.*

class ColorListAdapter : RecyclerView.Adapter<ColorListAdapter.ColorListViewHolder>() {
    var colorList = mutableListOf<ColorModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var selectedPosition: Int = 0
    var colorItemEventListener: ColorItemEventListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorListViewHolder =
        ColorListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_color, parent, false)
        )

    override fun onBindViewHolder(holder: ColorListViewHolder, position: Int) {
        holder.bind(colorList[position])
        if (selectedPosition == position)
            holder.containerView.selectItem.visibility = View.VISIBLE
        else
            holder.containerView.selectItem.visibility = View.INVISIBLE
    }

    override fun getItemCount(): Int =
        colorList.size


    inner class ColorListViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(colorModel: ColorModel) {
            containerView.root.setBackgroundResource(colorModel.color)

            containerView.setOnClickListener {

                notifyItemChanged(selectedPosition)
                selectedPosition = adapterPosition
                notifyItemChanged(selectedPosition)

                if (selectedPosition == RecyclerView.NO_POSITION) return@setOnClickListener

                colorItemEventListener!!.onItemClicked(colorModel)
            }
        }
    }

    interface ColorItemEventListener {
        fun onItemClicked(colorModel: ColorModel)
    }

}