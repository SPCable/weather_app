package com.paxcreation.weatherapp.presentation.ui.binding

import android.view.View
import androidx.databinding.BindingAdapter
import com.paxcreation.weatherapp.presentation.ui.custom_view.ToolbarCustomView

object ViewBinding {
    @JvmStatic
    @BindingAdapter("onClickRightNav")
    fun  setOnClickRightNav(view: ToolbarCustomView, onClickListener: View.OnClickListener) {
        view.setOnClickRight(onClickListener)
    }


    @JvmStatic
    @BindingAdapter("onClickLeftNav")
    fun  setOnClickLeftNav(view: ToolbarCustomView, onClickListener: View.OnClickListener) {
        view.setOnClickLeft(onClickListener)
    }
}