package com.hamiddev.todolist.data.repository.model

import androidx.annotation.DrawableRes

data class ColorModel(
    @DrawableRes val color:Int,
    var isSelect:Boolean=false
)